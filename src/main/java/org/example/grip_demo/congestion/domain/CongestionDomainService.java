package org.example.grip_demo.congestion.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CongestionDomainService {
    //클라이밍짐의 혼잡도와 관련된 도메인 로직을 처리..

    //먼저 클라이밍짐별 시간대별 혼잡도 불러오기
    public Map<Integer, Integer> getGymCongestionData(List<Congestion> congestions) {
        int businessHours = 12; //영업시간 일단 12시간으로 해서'
        if(congestions == null || congestions.isEmpty()){
            Map<Integer, Integer> emptyCongestionData = new HashMap<>();
            for (int i = 0; i < businessHours; i++){
                emptyCongestionData.put(i, 0);
            }//영업시간 아니거나 사람 없으면 시간대별로 0표시
            return emptyCongestionData;
        }

        Map<Integer, Integer> congestionData = new HashMap<>();

        for (Congestion congestion : congestions){

            LocalDateTime startTime = congestion.getStartTime();
            LocalDateTime endTime = congestion.getEndTime();
            //한번 포함된 인원수는 4시간동안 유지되어야함
            LocalDateTime finalTime = startTime.plusHours(4);

            int startHour = startTime.getHour();
            int finalHour = Math.min(finalTime.getHour(), endTime.getHour());

            int hour = startHour;
            while (hour <= finalHour) {

                Integer presentCount = congestionData.get(hour);

                if (presentCount == null) {
                    congestionData.put(hour, congestion.getPresentCount());
                } else {
                    congestionData.put(hour, presentCount + congestion.getPresentCount());
                }
                hour++;
            }
        }
        return congestionData;
    }

    //보여질 최근 혼잡도
    public Congestion getLatestCongestion(List<Congestion> congestions) {
        if (congestions == null || congestions.isEmpty()){
            throw new IllegalArgumentException("혼잡도 데이터가 없음");
        }
        return congestions.stream()
                .max(Comparator.comparing(Congestion::getCreatedAt))
                .orElseThrow(() -> new IllegalArgumentException("혼잡도 데이터가 없ㅇ,ㅁ"));
    }

    //그리고 지도에 표시되는 간단 혼잡도
    public int getMapCongestionLevel(Congestion congestions){
        if (congestions == null){
            throw new IllegalArgumentException("혼잡도 데이터가 null일 수 없음");
        }

        int presentCount = congestions.getPresentCount();
        int acceptableCount = congestions.getAcceptableCount();

        if(acceptableCount <= 0){//영업시간 이외
            return 0;
        }

        if (presentCount < 0){//이용 인원은 최소 0명 이상이어야함
            throw new IllegalArgumentException("현재 인원수가 음수일 수 없음");
        }

        double ratio = (double) presentCount/acceptableCount;

        if(ratio < 0.40){
            return 1;//여유(파랑색)
        }else if(ratio < 0.70){
            return 2;//보통(초록색)
        }else{
            return 3;//혼잡(빨강색)
        }
    }
}
