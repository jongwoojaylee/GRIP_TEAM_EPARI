package org.example.grip_demo.congestion.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CongestionDomainService {
    //Congestion이랑 CongestionRepository를 임플해서 기본적인것 DomainService에 필요한 기본적인것 구현

    //데이터 불러옴
    private final CongestionRepository congestionRepository;

    //먼저 혼잡도id로 혼잡도 데이터 가져오기
    public Optional<Congestion> findById(Long id){
        return congestionRepository.findById(id);
    }

    //그리고 List타입으로 혼잡도의 현재 인원 가져오기
    public List<Integer> getPresentCountByCongestion(List<Congestion> congestion){
        for (Congestion congestion1 : congestion) {
            congestion1.getPresentCount();
        }
        List<Integer> presentCounts = new ArrayList<>();
        return presentCounts;
    }

    //클라이밍짐id별로 가져오기?
    public List<Congestion> findByGymId(Long gymId){
        return congestionRepository.findByClimbingGymId(gymId);
    }

    //혼잡도 저장하기
    public void saveCongestion(Congestion congestion){
        congestionRepository.save(congestion);
    }
}
