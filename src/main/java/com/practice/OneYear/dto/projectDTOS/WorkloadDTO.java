package com.practice.OneYear.dto.projectDTOS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkloadDTO {
    private Long userId;
    private String userName;
    private Long totalIssues;
    private Long workloadScore;
}
