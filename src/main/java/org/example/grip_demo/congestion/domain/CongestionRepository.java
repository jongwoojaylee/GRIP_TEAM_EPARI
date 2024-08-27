package org.example.grip_demo.congestion.domain;

import org.example.grip_demo.congestion.interfaces.CongestionDTO;

import java.util.List;
import java.util.Optional;

//도메인로직에 필요한 비즈니스 메서드 선언
public interface CongestionRepository{

    Optional<Congestion> findById(Long id);
    void save(Congestion congestion);
    List<Congestion> findByClimbingGymId(Long GymId);
    static Optional<CongestionDTO> findByTimeZoneAndClimbingGym_Id(Integer hour, Long climbingGym_id);

}//실제로는 CongestionRepositoryImpl에서 구현됨.