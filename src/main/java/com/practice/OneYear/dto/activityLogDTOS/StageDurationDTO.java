package com.practice.OneYear.dto.activityLogDTOS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StageDurationDTO {

    private String fromStatus;

    private String toStatus;

    private long durationHours;

}
