package com.practice.OneYear.service.issueService;

import com.practice.OneYear.dto.issueDTOS.IssueInputDTO;
import com.practice.OneYear.dto.issueDTOS.IssueOutputDTO;
import com.practice.OneYear.entity.Issue;
import com.practice.OneYear.entity.Project;
import com.practice.OneYear.entity.User;
import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.entity.enums.IssueType;
import com.practice.OneYear.exceptions.ResourceNotFoundException;
import com.practice.OneYear.mapper.activityLogMapper.ActivityLogMapper;
import com.practice.OneYear.mapper.issueMapper.IssueMapper;
import com.practice.OneYear.repository.IssueRepository;
import com.practice.OneYear.repository.ProjectRepository;
import com.practice.OneYear.repository.UserRepository;
import com.practice.OneYear.service.activityLogService.ActivityLogService;
import com.practice.OneYear.specification.IssueSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final IssueMapper issueMapper;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    public IssueServiceImpl(IssueRepository issueRepository, ProjectRepository projectRepository, IssueMapper issueMapper, UserRepository userRepository, ActivityLogService activityLogService) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.issueMapper = issueMapper;
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
    }

    @Override
    public IssueOutputDTO addIssue(Long projectId, IssueInputDTO issueInputDTO, IssuePriority issuePriority, IssueType issueType) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project Not Found"));

        Issue issue = issueMapper.toEntityFromInputDtoIssue(issueInputDTO, issuePriority, issueType);

        // ✅ FIX: set project in issue
        issue.setProject(project);

        // (optional but good practice)
        project.getIssues().add(issue);
        Issue saved = issueRepository.save(issue);

        activityLogService.createActivityLog(saved,"Created Issue ID: " + issue.getId() + " by Reporter ID:" + issue.getReporter().getUserId());

        return issueMapper.toDtoFromIssueEntity(saved);
    }

    @Override
    public List<IssueOutputDTO> getIssues(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Project with id " + projectId + " not found")
        );

        List<IssueOutputDTO> list = project.getIssues()
                .stream()
                .map(x -> issueMapper.toDtoFromIssueEntity(x))
                .toList();

        return list;
    }

    @Override
    public IssueOutputDTO updateStatus(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(
                () -> new ResourceNotFoundException("Issue with id: " + issueId + " not found.")
        );
        IssueStatus status = issue.getStatus();
        switch (status){
            case NEW :
                issue.setStatus(IssueStatus.IN_PROGRESS);
                break;

            case IN_PROGRESS:
                issue.setStatus(IssueStatus.RESOLVED);
                break;

            case RESOLVED:
                issue.setStatus(IssueStatus.CLOSED);
                break;

            case CLOSED:
                issue.setStatus(IssueStatus.CLOSED);
                break;

            default:
                throw new RuntimeException("Not Found Status");
        }

        Issue save = issueRepository.save(issue);
        activityLogService.createActivityLog(save,"Updated Status of Issue ID: "+ save.getId() + " from " + status + " to " + save.getStatus());

        return issueMapper.toDtoFromIssueEntity(save);
    }

    @Override
    public IssueOutputDTO assignUserToIssue(Long issueId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found")
        );

        Issue issue = issueRepository.findById(issueId).orElseThrow(
                () -> new ResourceNotFoundException("Issue with id: " + issueId + " not found")
        );

        issue.setAssignee(user);

        if(!user.getAssignedIssues().contains(issue)){
            user.getAssignedIssues().add(issue);
        }

        Issue save = issueRepository.save(issue);

        return issueMapper.toDtoFromIssueEntity(save);
    }

    @Override
    public List<IssueOutputDTO> filterIssues(
            IssueStatus status,
            IssuePriority priority,
            IssueType type,
            Long assignedTo,
            Long createdBy
    ) {
        Specification<Issue> spec =
                IssueSpecification.filterIssues(status, priority, type, assignedTo, createdBy);

        List<Issue> issues = issueRepository.findAll(spec);

        return issues.stream().map(issueMapper::toDtoFromIssueEntity).toList();
    }
}
