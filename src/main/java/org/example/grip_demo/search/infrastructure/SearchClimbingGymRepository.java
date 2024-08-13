package org.example.grip_demo.search.infrastructure;

import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchClimbingGymRepository extends JpaRepository<ClimbingGym, Long> {
    Page<ClimbingGym> findByNameContaining(String keyword, Pageable pageable);
}
