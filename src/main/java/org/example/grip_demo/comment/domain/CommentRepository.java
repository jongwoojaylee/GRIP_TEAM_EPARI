package org.example.grip_demo.comment.domain;

import org.example.grip_demo.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findAll();
    Optional<Comment> findById(Long id);
    List<Comment> findByPost(Post post);
    void deleteById(Long id);
}
