package org.example.grip_demo.climbinggym.infrastructure;

import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.domain.ClimbingGymRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimbingGymRepositoryImpl extends JpaRepository<ClimbingGym, Long>, ClimbingGymRepository {
}
