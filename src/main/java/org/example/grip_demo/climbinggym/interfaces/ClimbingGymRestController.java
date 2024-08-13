package org.example.grip_demo.climbinggym.interfaces;

import org.example.grip_demo.climbinggym.application.ClimingGymService;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ClimbingGymRestController {
    private final ClimingGymService climingGymService;

    public ClimbingGymRestController(ClimingGymService climingGymService) {
        this.climingGymService = climingGymService;
    }

    @GetMapping("/api/climbinggym")
    public ClimbingGymDto getClimbingGym(@RequestParam("climbingid") Long climbingId) {
        Optional<ClimbingGym> climbingGym = climingGymService.getClimbingGym(climbingId);
        return climbingGym.map(gym -> new ClimbingGymDto(gym.getId(), gym.getName(), gym.getAddress(), gym.getMapX(), gym.getMapY()))
                .orElse(null);
    }
}
