package com.practice.OneYear.mapper.projectMapper;

import com.practice.OneYear.dto.projectDTOS.ProjectInputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectOutputDTO;
import com.practice.OneYear.entity.Project;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProjectMapper {

    public ProjectOutputDTO toOutputDTO(Project project){
        if (project == null){
            return null;
        }
        return ProjectOutputDTO.builder()
                .projectID(project.getProjectId())
                .projectName(project.getName())
                .projectDescription(project.getDescription())
                .projectCreatedAt(project.getCreatedAt())
                .build();
    }

    public Project toEntityFromInputDTO(ProjectInputDTO projectInputDTO){
        if (projectInputDTO == null){
            return null;
        }

        return Project.builder()
                .name(projectInputDTO.getProjectName())
                .description(projectInputDTO.getProjectDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
