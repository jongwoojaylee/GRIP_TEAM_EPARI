package org.example.grip_demo.comment.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.comment.domain.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }


    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
