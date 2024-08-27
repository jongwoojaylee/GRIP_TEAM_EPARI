package org.example.grip_demo.map.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.climbinggym.application.ClimingGymService;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.interfaces.ClimbingGymDto;
import org.example.grip_demo.congestion.application.CongestionService;
import org.example.grip_demo.congestion.domain.CongestionDomainService;
import org.example.grip_demo.congestion.interfaces.CongestionDto;
import org.example.grip_demo.map.application.SearchApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MapRestController {
    private final ClimingGymService climingGymService;
    private final CongestionDomainService congestionService;

    public MapRestController(ClimingGymService climingGymService, CongestionDomainService congestionService) {
        this.climingGymService = climingGymService;
        this.congestionService = congestionService;
    }


    @GetMapping("/api/climbinggyms")
    public List<ClimbingGymDto> main() {
        List<ClimbingGym> gyms = climingGymService.getAllClimbingGyms();
        return gyms.stream()
                .map(gym -> new ClimbingGymDto(gym.getId(), gym.getName(),
                        gym.getAddress(), gym.getMapX(), gym.getMapY(),
                        gym.getAcceptableCount()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/search/climbinggyms")
    public List<ClimbingGymDto> searchGyms(@RequestParam("keyword") String keyword) {
        List<ClimbingGym> gyms = climingGymService.searchClimbingGyms(keyword);
        return gyms.stream()
                .map(gym -> new ClimbingGymDto(gym.getId(), gym.getName(),
                        gym.getAddress(), gym.getMapX(), gym.getMapY(),
                        gym.getAcceptableCount()
                ))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/climbinggym/{gymId}/congestion")
    public ResponseEntity<CongestionDto> getCurrentCongestion(@PathVariable Long gymId, @RequestParam int hour) {
        Optional<CongestionDto> congestion = congestionService.getCongestion(hour, gymId);
        return congestion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
