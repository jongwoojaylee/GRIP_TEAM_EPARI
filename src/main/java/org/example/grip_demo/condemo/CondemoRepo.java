package org.example.grip_demo.condemo;

import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CondemoRepo extends JpaRepository<CongestionDemo, Long> {
    List<CongestionDemo> findAllByTimeZoneAndClimbingGym_Id(Integer timeZone, Long climbingGym_id);
    Optional<CongestionDemoDto> findByTimeZoneAndClimbingGym_Id(Integer hour, Long climbingGym_id);

    List<CongestionDemo> findAllByClimbingGymIn(List<ClimbingGym> gymIds);
}
