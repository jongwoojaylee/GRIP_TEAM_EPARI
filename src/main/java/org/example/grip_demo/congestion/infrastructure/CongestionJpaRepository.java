package org.example.grip_demo.congestion.infrastructure;

import org.example.grip_demo.congestion.domain.Congestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CongestionJpaRepository extends JpaRepository<Congestion, Long> {
    List<Congestion> findByClimbingGymId(Long climbingGymId);
}