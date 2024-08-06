package org.example.grip_demo.congestion.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.domain.CongestionDomainService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CongestionService {

    private final CongestionDomainService congestionDomainService;

    //클라이밍장별 현재인원 가져옴
    public List<Integer> getPresentCountByGymId(Long gymId){
        List<Congestion> congestion = congestionDomainService.findByGymId(gymId);
        List<Integer> presentCounts =congestionDomainService.getPresentCountByCongestion(congestion);
        return presentCounts;
    }

    //혼잡도 계산
    public double getCongestionRatio(Congestion congestion){
        int presentCount = congestion.getPresentCount();
        ClimbingGym climbingGym = congestion.getClimbingGym();
        int acceptableCount = climbingGym.getAcceptableCount();

        if(congestion.getPresentCount() == 0){
            return +0; //이용자가 없으면 혼잡도 계산에 더하지 않음
        }
        return (double) (congestion.getPresentCount() / climbingGym.getAcceptableCount() * 100);

        //
    }
}