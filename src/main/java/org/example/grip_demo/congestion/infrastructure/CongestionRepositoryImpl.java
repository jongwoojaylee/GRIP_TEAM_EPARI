package org.example.grip_demo.congestion.infrastructure;

import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.domain.CongestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CongestionRepositoryImpl implements CongestionRepository {
    private final CongestionJpaRepository jpaRepository;

    public CongestionRepositoryImpl(CongestionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Congestion> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void save(Congestion congestion) {
        jpaRepository.save(congestion);
    }

    @Override
    public List<Congestion> findByClimbingGymId(Long id) {
        return jpaRepository.findByClimbingGymId(id);
    }

}