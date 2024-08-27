package org.example.grip_demo.congestion.domain;

import org.example.grip_demo.condemo.CongestionDemoDto;
import org.example.grip_demo.congestion.interfaces.CongestionDto;

import java.util.List;
import java.util.Optional;

//도메인로직에 필요한 비즈니스 메서드 선언
public interface CongestionRepository{

    Optional<Congestion> findById(Long id);
    void save(Congestion congestion);
    List<Congestion> findByClimbingGymId(Long GymId);
    Optional<CongestionDto> findByTimeZoneAndClimbingGym_Id(int hour, Long climbingGym_id);

}//실제로는 CongestionRepositoryImpl에서 구현됨.