package com.practice.OneYear.dto.activityLogDTOS;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IssueVelocityDTO {

    private Long issueId;

    private long durationHours;

    private List<StageDurationDTO> stageDurations;

    private StageDurationDTO slowestStage;
}
