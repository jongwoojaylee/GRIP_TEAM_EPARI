package org.example.grip_demo.comment.domain;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }




}
