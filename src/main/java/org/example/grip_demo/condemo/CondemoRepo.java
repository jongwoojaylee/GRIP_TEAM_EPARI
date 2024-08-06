package org.example.grip_demo.condemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CondemoRepo extends JpaRepository<CongestionDemo, Long> {
    List<CongestionDemo> findByTimeZoneAndClimbingGym_Id(Integer timeZone, Long climbingGym_id);
}
