package com.practice.OneYear.dto.issueDTOS;

import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.entity.enums.IssueType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class IssueOutputDTO {
    private Long id;
    private String title;
    private String description;
    private IssueStatus status;
    private IssuePriority priority;
    private IssueType type;
    private String projectName;
    private String reporterName;
    private String assigneeName;
    private LocalDateTime updatedAt;
}
