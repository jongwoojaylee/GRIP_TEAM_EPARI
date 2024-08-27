package org.example.grip_demo.congestion.infrastructure;

import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.interfaces.CongestionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CongestionRepository extends JpaRepository<Congestion, Long> {
    Optional<Congestion> findById(Long id);
    List<Congestion> findByClimbingGymId(Long climbingGymId);
    Optional<CongestionDto> findByTimeZoneAndClimbingGym_Id(int hour, Long climbingGym_id);
}