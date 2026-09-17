package com.practice.OneYear.dto.projectDTOS;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProjectStatsDTO {

    private Long projectId;
    private Map<String, Long> statusCounts;
    private Map<String,Long> priorityCounts;
    private Long totalIssues;
    private Long resolvedIssues;
    private double projectHealth;

}
