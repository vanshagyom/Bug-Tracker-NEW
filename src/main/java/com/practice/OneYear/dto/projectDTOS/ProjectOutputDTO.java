package com.practice.OneYear.dto.projectDTOS;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectOutputDTO {
    private Long projectID;

    private String projectName;

    private String projectDescription;

    private LocalDateTime projectCreatedAt;
}
