package com.practice.OneYear.service.projectService;

import com.practice.OneYear.dto.projectDTOS.ProjectInputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectOutputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectStatsDTO;
import com.practice.OneYear.dto.projectDTOS.WorkloadDTO;
import com.practice.OneYear.entity.Issue;
import com.practice.OneYear.entity.Project;
import com.practice.OneYear.entity.User;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.exceptions.ResourceNotFoundException;
import com.practice.OneYear.mapper.projectMapper.ProjectMapper;
import com.practice.OneYear.repository.ProjectRepository;
import com.practice.OneYear.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectOutputDTO> getAllProjects() {
        List<Project> all = projectRepository.findAll();

        List<ProjectOutputDTO> list = all.stream().map(x -> projectMapper.toOutputDTO(x)).toList();

        return list;
    }

    @Override
    public ProjectOutputDTO addProject(ProjectInputDTO projectInputDTO) {
        Project entityFromInputDTO = projectMapper.toEntityFromInputDTO(projectInputDTO);
        Project save = projectRepository.save(entityFromInputDTO);
        return projectMapper.toOutputDTO(save);
    }

    @Override
    public void assignMembersToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        project.getTeamMembers().add(user);
        projectRepository.save(project);

    }

    @Override
    public ProjectStatsDTO getProjectStats(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project with id: " + projectId + " not found")
        );

        Map<String, Long> collect =
                project.getIssues()
                        .stream()
                        .collect(Collectors
                                .groupingBy(x -> x.getStatus().toString(),
                                        Collectors.counting()
                                )
                        );

        Map<String, Long> collect1 = project.getIssues().stream().collect(Collectors.groupingBy(
                x -> x.getPriority().toString(), Collectors.counting()
        ));

        long count = project.getIssues().stream().count();

        long count1 = project.getIssues().stream().filter(x -> x.getStatus() == IssueStatus.RESOLVED).count();

        double health = (double) count1 / count;

        ProjectStatsDTO build = ProjectStatsDTO.builder()
                .projectId(projectId)
                .statusCounts(collect)
                .priorityCounts(collect1)
                .totalIssues(count)
                .resolvedIssues(count1)
                .projectHealth(health)
                .build();

        return build;

    }

    @Override
    public List<WorkloadDTO> calculateWorkload(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("Project not found with id:" + projectId)
        );

        return project.getTeamMembers().stream().map(x -> {

            List<Issue> list = project.getIssues().stream().filter(y -> y.getAssignee() != null && y.getAssignee().getUserId().equals(x.getUserId())).toList();

            long count = list.stream().count();

            long sum = list.stream().mapToLong(a ->
                    switch (a.getPriority()) {
                        case URGENT -> 5;

                        case HIGH -> 3;

                        case MEDIUM -> 2;

                        case LOW -> 1;
                    }).sum();
            return WorkloadDTO.builder()
                    .userId(x.getUserId())
                    .userName(x.getName())
                    .totalIssues(count)
                    .workloadScore(sum)
                    .build();
        }).toList();
    }
}
