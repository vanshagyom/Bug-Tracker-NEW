package com.practice.OneYear.controller;

import com.practice.OneYear.dto.issueDTOS.IssueInputDTO;
import com.practice.OneYear.dto.issueDTOS.IssueOutputDTO;
import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.entity.enums.IssueType;
import com.practice.OneYear.service.issueService.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<IssueOutputDTO> postAnIssue(@PathVariable Long projectId,
                                                      @Valid @RequestBody IssueInputDTO issueInputDTO,
                                                      @RequestParam IssueType issueType,
                                                      @RequestParam IssuePriority issuePriority){
        return ResponseEntity.ok(issueService.addIssue(projectId,issueInputDTO, issuePriority, issueType));
    }

    @GetMapping("/getIssues/{projectId}")
    public ResponseEntity<List<IssueOutputDTO>> getIssuesProjectWise(@PathVariable Long projectId){
        return ResponseEntity.ok(issueService.getIssues(projectId));
    }

    @PatchMapping("/updateStatus/{issueId}")
    public ResponseEntity<IssueOutputDTO> updateIssueStatus(@PathVariable Long issueId){
        return ResponseEntity.ok(issueService.updateStatus(issueId));
    }

    @PostMapping("/assign/{issueId}")
    public ResponseEntity<IssueOutputDTO> assignUser(@PathVariable Long issueId, @RequestParam Long userId){
        return ResponseEntity.ok(issueService.assignUserToIssue(issueId,userId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<IssueOutputDTO>> getFilteredData(
            @RequestParam IssueStatus status,
            @RequestParam IssuePriority priority,
            @RequestParam IssueType type,
            @RequestParam Long assignedTo,
            @RequestParam Long createdBy
    ){
        return ResponseEntity.ok(issueService.filterIssues(status, priority, type, assignedTo, createdBy));
    }
}
