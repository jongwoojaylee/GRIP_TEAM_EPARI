package org.example.grip_demo.congestion.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.domain.ClimbingGymDomainService;
import org.example.grip_demo.climbinggym.domain.ClimbingGymRepository;
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

    //클라이밍장별 현재인원 가져옴
    public List<Integer> getPresentCountByGymId(Long gymId){
        List<Congestion> congestionList = congestionDomainService.findByGymId(gymId);
        List<Integer> presentCounts =congestionDomainService.getPresentCountByCongestion(congestionList);
        return presentCounts;
    }

    //지도표시 혼잡도
    public Map<String, Object> getMapCongestionLevel(Long gymId){
        List<Congestion> congestionList = congestionDomainService.findByGymId(gymId);
        Optional<ClimbingGym> accCount = climbingGymRepository.findById(gymId);

//        ClimbingGym climbingGym = accCount.get();
        ClimbingGym climbingGym = accCount.orElseThrow(() -> new IllegalArgumentException("Climbing gym not found"));
        LocalTime currentTime = LocalTime.now();

        int totalCount = 0;
        for(Congestion congestion : congestionList){
//            LocalTime congestionTime = LocalTime.parse(congestion.getTimeZone());
            int hour = congestion.getTimeZone();
            String timeString = String.format("%02d:00", hour);
            LocalTime congestionTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

            if(currentTime.isAfter(congestionTime) && currentTime.isBefore(congestionTime.plusHours(4))){
                totalCount += congestion.getPresentCount();
            }
        }

        //일단 100으로 설정
        int acceptableCount = climbingGym.getAcceptableCount();
        double ratio = (double) totalCount / acceptableCount * 100;

        String conLevel;
        if(ratio < 40.0){
            conLevel = "여유";
        }else if(ratio < 70.0){
            conLevel = "보통";
        }else{
            conLevel = "혼잡";
        }

        //date타입인 것을 다른 형태로 변환시켜주는 class
        //format() : 형태 변환시키는 함수
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String time = simpleDateFormat.format(now);//지금시간대

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

//    //특정시간대 현재 인원수
//    public Integer getPresentCountByGymIdAndTimeZone(Long gymId, int timeZone) {
//        List<Congestion> congestions = congestionDomainService.findByGymId(gymId);
//
//        for (Congestion congestion : congestions){
//            if(congestion.getTimeZone() == timeZone){
//                return congestion.getPresentCount();
//            }
//        }
//        //인원 없을때
//        return 0;
//    }

    //클라이밈짐별로 현재인원수 불러와야함

    //지도에 간단한 혼잡도 표시 -- 보류
}