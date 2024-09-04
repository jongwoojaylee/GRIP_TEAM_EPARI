package org.example.grip_demo.climbinggym.domain;

import org.example.grip_demo.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageablePostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByClimbingGym_Id(Long climbingGymId, Pageable pageable);
}
