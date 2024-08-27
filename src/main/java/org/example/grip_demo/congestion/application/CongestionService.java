package org.example.grip_demo.congestion.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.domain.ClimbingGymDomainService;
import org.example.grip_demo.climbinggym.domain.ClimbingGymRepository;
import org.example.grip_demo.condemo.CongestionDemoDto;
import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.domain.CongestionDomainService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CongestionService {

    private final CongestionDomainService congestionDomainService;
    private final ClimbingGymDomainService climbingGymDomainService;
    private final ClimbingGymRepository climbingGymRepository;

    public Optional<CongestionDemoDto> getCongestionDemo(int hour, Long gymId) {
        return climbingGymRepository.findByTimeZoneAndClimbingGym_Id(hour, gymId);
    }

    //클라이밍장별 현재인원 가져옴
    public List<Integer> getPresentCountByGymId(Long gymId){
        List<Congestion> congestionList = congestionDomainService.findByGymId(gymId);
        List<Integer> presentCounts =congestionDomainService.getPresentCountByCongestion(congestionList);
        return presentCounts;
    }

    //지도표시 혼잡도
    public Map<String, Object> getMapCongestionLevel(Long gymId){
        List<Congestion> congestionList = congestionDomainService.findByGymId(gymId);
        List<Integer> presentCounts =congestionDomainService.getPresentCountByCongestion(congestionList);

        //적정 인원수 100으로 설정
        ClimbingGym climbingGym = climbingGymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid gym ID: " + gymId));
                //-> Optional의 인자가 null일 경우 예외처리
        int acceptableCount = climbingGym.getAcceptableCount();
        int presentCount = presentCounts.get(presentCounts.size()-1);//마지막값. 마지막 시간
        double ratio = ((double) presentCount / acceptableCount) * 100;

        //확인 -- 완료
        System.out.println("ratio: " + ratio);

        String conLevel;
        if(ratio < 55.0){
            conLevel = "여유";
        }else if(ratio < 75.0){
            conLevel = "보통";
        }else{
            conLevel = "혼잡";
        }

        //date타입인 것을 다른 형태로 변환시켜주기
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String time = simpleDateFormat.format(now);//지금시간대. 형태 변환시키기

        Map<String, Object> level = new HashMap<>();
        level.put("ratio", ratio);
        level.put("conLevel", conLevel);
        level.put("time", time);

        return level;
    }

    //혼잡도 계산
    public double getCongestionRatio(Congestion congestion){
        int presentCount = congestion.getPresentCount();
        ClimbingGym climbingGym = congestion.getClimbingGym();
        int acceptableCount = climbingGym.getAcceptableCount();

        if(congestion.getPresentCount() == 0){
            return 0; //이용자가 없으면 혼잡도 계산에 더하지 않음
        }
        return (double) congestion.getPresentCount() / climbingGym.getAcceptableCount() * 100;
    }
}