package org.example.grip_demo.congestion.interfaces;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.climbinggym.application.ClimingGymService;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.domain.ClimbingGymDomainService;
import org.example.grip_demo.climbinggym.infrastructure.ClimbingGymRepositoryImpl;
import org.example.grip_demo.congestion.application.CongestionService;
import org.example.grip_demo.congestion.domain.Congestion;
import org.example.grip_demo.congestion.domain.CongestionDomainService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/test")
@Slf4j
public class CongestionRestController {
    private final CongestionService congestionService;
    private final CongestionDomainService congestionDomainService;
    private final ClimingGymService climingGymService;
    private final ClimbingGymDomainService climbingGymDomainService;
    private final ClimbingGymRepositoryImpl climbingGymRepositoryImpl;
    //일단 혼잡도를 하나씩 꺼내

    //클라이밍짐 현재인원수
    @GetMapping("/presentCount/{gymId}")
    public String getPresentCountByGymId(@PathVariable Long gymId){
        List<Integer> presentCounts = congestionService.getPresentCountByGymId(gymId);

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("climbingGym", gymId.toString());

        JsonArray timeZone = new JsonArray();
        for(int i = 10; i < 25; i++){
            JsonObject jsonObj2 = new JsonObject();
            jsonObj2.addProperty("time "+ i, presentCounts.get(i-10));
            timeZone.add(jsonObj2);
        }

        jsonObj.add("congestion", timeZone);

        return jsonObj.toString();
    }

    //지도에 표시되는 간단혼잡도
    @GetMapping("/mapCongestion/{gymId}")
    public String getCongestionRatio(@PathVariable Long gymId){

        Map<String, Object> congestionData = congestionService.getMapCongestionLevel(gymId);

        JsonObject level = new JsonObject();
        level.addProperty("presentRatio", (double) congestionData.get("ratio"));
        level.addProperty("level", (String) congestionData.get("conLevel"));
        level.addProperty("timeZone", (String) congestionData.get("time"));

        return level.toString();
    }

    //특정 시간대의 클라이밍짐 현재인원
//    @GetMapping("/presentCount/{gymId}/{hour}")
//    public Integer getPresentCountByGymIdAndHour(@PathVariable Long gymId, @PathVariable int timeZone){
//        List<Integer> presentCounts = congestionService.getPresentCountByGymId(gymId);
//
//        JsonObject specHourCount = new JsonObject();
//        specHourCount.addProperty("climbingGym", gymId.toString());
//        specHourCount.addProperty("timeZone", timeZone);
//
//        JsonArray timeAndCount = new JsonArray();
//        timeAndCount.add(presentCounts.get(timeZone));
//
//        return specHourCount.get;
//    }

    // 혼잡도 계산하기
//    @PostMapping("/congestion")
//    public double calculateCongestion(@RequestBody Congestion congestion){
//        return congestionService.getCongestionRatio(congestion);
//    }

}
