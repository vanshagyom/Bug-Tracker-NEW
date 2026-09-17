package com.practice.OneYear.dto.issueDTOS;

import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueInputDTO {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

//    @NotNull(message = "Type is required")
//    private IssueType type;
//
//    @NotNull(message = "Priority is required")
//    private IssuePriority priority;

    @NotNull(message = "Reporter ID is required")
    private Long reporterId;

    private Long assigneeId;
}
