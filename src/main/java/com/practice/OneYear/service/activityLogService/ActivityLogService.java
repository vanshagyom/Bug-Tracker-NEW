package com.practice.OneYear.service.activityLogService;

import com.practice.OneYear.dto.activityLogDTOS.ActivityLogOutputDTO;
import com.practice.OneYear.dto.activityLogDTOS.IssueVelocityDTO;
import com.practice.OneYear.entity.Issue;

import java.util.List;

public interface ActivityLogService {
    void createActivityLog(Issue issue, String action);

    List<ActivityLogOutputDTO> getActivityLog();

    IssueVelocityDTO getIssueVelocity(Long issueId);
}
