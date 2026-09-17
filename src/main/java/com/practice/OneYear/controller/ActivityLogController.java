package com.practice.OneYear.controller;

import com.practice.OneYear.dto.activityLogDTOS.ActivityLogOutputDTO;
import com.practice.OneYear.dto.activityLogDTOS.IssueVelocityDTO;
import com.practice.OneYear.service.activityLogService.ActivityLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityLogOutputDTO>> getActivityLog(){
        return ResponseEntity.ok(activityLogService.getActivityLog());
    }

    @GetMapping("/velocity/{issueVelocity}")
    public ResponseEntity<IssueVelocityDTO> getIssuesVelocityStats(@PathVariable Long issueVelocity){
        return ResponseEntity.ok(activityLogService.getIssueVelocity(issueVelocity));
    }

}
