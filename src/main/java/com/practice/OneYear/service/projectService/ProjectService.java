package com.practice.OneYear.service.projectService;

import com.practice.OneYear.dto.projectDTOS.ProjectInputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectOutputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectStatsDTO;
import com.practice.OneYear.dto.projectDTOS.WorkloadDTO;

import java.util.List;

public interface ProjectService {
    List<ProjectOutputDTO> getAllProjects();

    ProjectOutputDTO addProject(ProjectInputDTO projectInputDTO);

    void assignMembersToProject(Long projectId,Long userId);

    ProjectStatsDTO getProjectStats(Long projectId);

    List<WorkloadDTO> calculateWorkload(Long projectId);
}
