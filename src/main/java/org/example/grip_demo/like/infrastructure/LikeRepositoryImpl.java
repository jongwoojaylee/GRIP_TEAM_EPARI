package org.example.grip_demo.like.infrastructure;

import org.example.grip_demo.like.domain.Like;
import org.example.grip_demo.like.domain.LikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepositoryImpl extends JpaRepository<Like, Long>, LikeRepository {
}
