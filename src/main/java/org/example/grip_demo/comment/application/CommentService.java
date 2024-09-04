package org.example.grip_demo.comment.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.comment.domain.CommentDomainService;
import org.example.grip_demo.comment.domain.CommentRepository;
import org.example.grip_demo.post.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentDomainService commentDomainService;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment updateComment(Long id, String updateCommentText) {
        return commentDomainService.updateComment(id,updateCommentText);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public void deleteComment(Long commentId) {
        commentDomainService.deleteComment(commentId);
    }
}
