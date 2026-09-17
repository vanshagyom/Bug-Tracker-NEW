package com.practice.OneYear.mapper.issueMapper;

import com.practice.OneYear.dto.issueDTOS.IssueInputDTO;
import com.practice.OneYear.dto.issueDTOS.IssueOutputDTO;
import com.practice.OneYear.entity.Issue;
import com.practice.OneYear.entity.Project;
import com.practice.OneYear.entity.User;
import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.entity.enums.IssueType;
import com.practice.OneYear.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IssueMapper {

    private final UserRepository userRepository;

    public IssueMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Issue toEntityFromInputDtoIssue(IssueInputDTO issueInputDTO, IssuePriority issuePriority, IssueType issueType){
        if (issueInputDTO == null){
            return null;
        }

        User reporter = userRepository.findById(issueInputDTO.getReporterId()).orElseThrow(
                () -> new RuntimeException("User Reporter not found")
        );

        User assignee = null;

        if (issueInputDTO.getAssigneeId() != null) {
            assignee = userRepository.findById(issueInputDTO.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
        }

        return Issue.builder()
                .title(issueInputDTO.getTitle())
                .description(issueInputDTO.getDescription())
                .type(issueType)
                .priority(issuePriority)
                .status(IssueStatus.NEW)
                .reporter(reporter)
                .assignee(assignee)
                .build();
    }

    public IssueOutputDTO toDtoFromIssueEntity(Issue issue){
        if (issue == null){
            return null;
        }

        return IssueOutputDTO.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .status(issue.getStatus())
                .priority(issue.getPriority())
                .type(issue.getType())
                .projectName(issue.getProject().getName())
                .reporterName(issue.getReporter().getName())
                .assigneeName(
                        issue.getAssignee() != null ? issue.getAssignee().getName() : null
                )
                .updatedAt(issue.getUpdatedAt())
                .build();
    }
}