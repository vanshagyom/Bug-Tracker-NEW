package com.practice.OneYear.service.commentService;

import com.practice.OneYear.dto.commentDTOS.CommentInputDTO;
import com.practice.OneYear.dto.commentDTOS.CommentOutputDTO;
import com.practice.OneYear.entity.Comment;
import com.practice.OneYear.entity.Issue;
import com.practice.OneYear.entity.User;
import com.practice.OneYear.exceptions.ResourceNotFoundException;
import com.practice.OneYear.mapper.commentMapper.CommentMapper;
import com.practice.OneYear.repository.CommentRepository;
import com.practice.OneYear.repository.IssueRepository;
import com.practice.OneYear.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, IssueRepository issueRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentOutputDTO postComment(Long issueID, CommentInputDTO commentInputDTO) {
        Long userID = commentInputDTO.getUserID();
        User user = userRepository.findById(userID).orElseThrow(
                () -> new ResourceNotFoundException("User with Id: " + userID + " not found")
        );
        Issue issue = issueRepository.findById(issueID).orElseThrow(
                () -> new ResourceNotFoundException("Issue with id: " + issueID + " not found")
        );
        Comment entity = commentMapper.toEntity(commentInputDTO);


        entity.setIssue(issue);

        Comment save = commentRepository.save(entity);
        return commentMapper.toOutputDto(save);
    }

    @Override
    public List<CommentOutputDTO> allCommentsOnIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(
                () -> new ResourceNotFoundException("Issue not found")
        );
        List<CommentOutputDTO> list =
                issue.getComments()
                        .stream()
                        .map(x -> commentMapper.toOutputDto(x))
                        .toList();
        return list;
    }
}
