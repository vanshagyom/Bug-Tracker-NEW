package com.practice.OneYear.dto.projectDTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectInputDTO {

    @NotBlank(message = "project name can't be empty")
    private String projectName;

    @NotBlank(message = "project description can't be empty")
    @Size(min = 5, message = "project description must be at least 5 characters")
    private String projectDescription;

}
