package com.practice.OneYear.controller;

import com.practice.OneYear.dto.projectDTOS.ProjectInputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectOutputDTO;
import com.practice.OneYear.dto.projectDTOS.ProjectStatsDTO;
import com.practice.OneYear.dto.projectDTOS.WorkloadDTO;
import com.practice.OneYear.service.projectService.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectOutputDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping
    public ResponseEntity<ProjectOutputDTO> addProject(@Valid @RequestBody ProjectInputDTO projectInputDTO) {
        return ResponseEntity.ok(projectService.addProject(projectInputDTO));
    }

    @PostMapping("/assign/{projectId}/to/{userId}")
    public ResponseEntity<String> addMembersToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.assignMembersToProject(projectId, userId);
        return ResponseEntity.ok("User added to Project successfully");
    }

    @GetMapping("stats/{projectId}")
    public ResponseEntity<ProjectStatsDTO> getProjectsStats(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectStats(projectId));
    }

    @GetMapping("workload/{projectId}")
    public ResponseEntity<List<WorkloadDTO>> getUserWorkload(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.calculateWorkload(projectId));
    }

}
