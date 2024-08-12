package org.example.grip_demo.map.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.climbinggym.application.ClimingGymService;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.interfaces.ClimbingGymDto;
import org.example.grip_demo.map.application.SearchApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MapRestController {
    private final SearchApiService searchApiService;
    private final ClimingGymService climingGymService;

    public MapRestController(SearchApiService naverApiService, ClimingGymService climingGymService) {
        this.searchApiService = naverApiService;
        this.climingGymService = climingGymService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return searchApiService.searchLocal(query);
    }

    @GetMapping("/api/climbinggyms")
    public List<ClimbingGymDto> main() {
        List<ClimbingGym> gyms = climingGymService.getAllClimbingGyms();
        return gyms.stream()
                .map(gym -> new ClimbingGymDto(gym.getId(), gym.getName(), gym.getAddress(), gym.getMapX(), gym.getMapY()))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/search/climbinggyms")
    public List<ClimbingGymDto> searchGyms(@RequestParam("keyword") String keyword) {
        List<ClimbingGym> gyms = climingGymService.searchClimbingGyms(keyword);
        return gyms.stream()
                .map(gym -> new ClimbingGymDto(gym.getId(), gym.getName(), gym.getAddress(), gym.getMapX(), gym.getMapY()))
                .collect(Collectors.toList());
    }
}
