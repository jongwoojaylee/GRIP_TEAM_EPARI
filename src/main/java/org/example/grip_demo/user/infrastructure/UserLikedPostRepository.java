package org.example.grip_demo.user.infrastructure;

import org.example.grip_demo.like.domain.Like;
import org.example.grip_demo.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikedPostRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserId(Long userId);
}
