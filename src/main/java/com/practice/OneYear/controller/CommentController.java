package com.practice.OneYear.controller;

import com.practice.OneYear.dto.commentDTOS.CommentInputDTO;
import com.practice.OneYear.dto.commentDTOS.CommentOutputDTO;
import com.practice.OneYear.service.commentService.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{IssueId}")
    public ResponseEntity<CommentOutputDTO> postComment(@PathVariable Long IssueId, @Valid @RequestBody CommentInputDTO commentInputDTO){
        return ResponseEntity.ok(commentService.postComment(IssueId,commentInputDTO));
    }

    @GetMapping("/comments/{issueId}")
    public ResponseEntity<List<CommentOutputDTO>> getAllCommentsOnThisIssue(@PathVariable Long issueId){
        return ResponseEntity.ok(commentService.allCommentsOnIssue(issueId));
    }
}
