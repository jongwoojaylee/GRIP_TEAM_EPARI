package org.example.grip_demo.like.domain;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {
    Like save(Like like);
    void delete(Like like);
    void deleteById(Long id);
    Optional<Like> findById(Long id);
    List<Like> findAll();
    List<Like> findAllByPostId(Long id);
    Like findByPostIdAndUserId(Long postId, Long userId);
}
