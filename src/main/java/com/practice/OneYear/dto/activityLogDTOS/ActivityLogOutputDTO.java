package com.practice.OneYear.dto.activityLogDTOS;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityLogOutputDTO {
    private Long id;
    private String action;
    private LocalDateTime timestamp;
}
