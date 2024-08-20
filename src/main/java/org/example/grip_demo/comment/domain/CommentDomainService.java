package org.example.grip_demo.comment.domain;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentDomainService {

    @Autowired
    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
    public List<Comment> getComment(Long id) {
        return commentRepository.findAll();
    }

    public Comment updateComment(Long id, String updateCommentText){
        Comment updateComment = commentRepository.findById(id).orElse(null);
        updateComment.setCommentText(updateCommentText);
        updateComment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(updateComment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }




}
