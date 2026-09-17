package com.practice.OneYear.service.commentService;

import com.practice.OneYear.dto.commentDTOS.CommentInputDTO;
import com.practice.OneYear.dto.commentDTOS.CommentOutputDTO;

import java.util.List;

public interface CommentService {

    CommentOutputDTO postComment(Long issueID, CommentInputDTO commentInputDTO);

    List<CommentOutputDTO> allCommentsOnIssue(Long issueId);
}
