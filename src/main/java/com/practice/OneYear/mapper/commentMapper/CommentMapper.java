package com.practice.OneYear.mapper.commentMapper;

import com.practice.OneYear.dto.commentDTOS.CommentInputDTO;
import com.practice.OneYear.dto.commentDTOS.CommentOutputDTO;
import com.practice.OneYear.entity.Comment;
import com.practice.OneYear.entity.User;
import com.practice.OneYear.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final UserRepository userRepository;

    public CommentMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Comment toEntity(CommentInputDTO inputDTO) {

        User user = userRepository.findById(inputDTO.getUserID()).orElseThrow(
                () -> new RuntimeException("User with id:" + inputDTO.getUserID() + " not found in mapper(comment).")
        );

        return Comment.builder()
                .message(inputDTO.getMessage())
                .author(user)
                .build();
    }

    public CommentOutputDTO toOutputDto(Comment comment) {
        return CommentOutputDTO.builder()
                .commentId(comment.getCommentId())
                .message(comment.getMessage())
                .authorName(comment.getAuthor().getName())
                .createAt(comment.getCreated_at())
                .build();
    }
}
