package com.practice.OneYear.dto.commentDTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentInputDTO {

    @NotBlank(message = "message can't be blank")
    private String message;

    private Long userID;
}
