package org.example.grip_demo.congestion.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongestionRepository extends JpaRepository<Congestion, Long> {
    List<Congestion> findByClimbingGymId(Long id);
}
