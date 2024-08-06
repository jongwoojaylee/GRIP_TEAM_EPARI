package org.example.grip_demo.congestion.infrastructure;

import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.domain.CongestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongestionRepositoryImpl extends JpaRepository<Congestion, Long>, CongestionRepository {
}