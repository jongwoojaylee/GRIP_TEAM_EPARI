package org.example.grip_demo.congestion.application;

import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.domain.CongestionDomainService;
import org.example.grip_demo.congestion.domain.CongestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CongestionService {
    private final CongestionRepository congestionRepository;
    private final CongestionDomainService congestionDomainService;

    public CongestionService(CongestionRepository congestionRepository, CongestionDomainService congestionDomainService){
        this.congestionRepository = congestionRepository;
        this.congestionDomainService = congestionDomainService;
    }

    //혼잡도 데이터
    public Map<Integer, Integer> getGymCongestionData(Long CLIMBING_GYM_ID){
        List<Congestion> congestions = congestionRepository.findByClimbingGymId(CLIMBING_GYM_ID);
        return congestionDomainService.getGymCongestionData(congestions);
    }

    //지도에 간단한 혼잡도
    public int getMapCongestionLevel(Long CLIMBING_GYM_ID){
        List<Congestion> congestions = congestionRepository.findByClimbingGymId(CLIMBING_GYM_ID);
        if(congestions.isEmpty()){
            return 0;
        }
        Congestion latestCongestion = congestionDomainService.getLatestCongestion(congestions);
        try{
            return congestionDomainService.getMapCongestionLevel(latestCongestion);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
