package com.practice.OneYear.dto.commentDTOS;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentOutputDTO {
    private Long commentId;

    private String message;

    private String authorName;

    private LocalDateTime createAt;
}
