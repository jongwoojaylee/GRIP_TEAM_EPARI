package org.example.grip_demo.condemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConDemoService {
    private final CondemoRepo repo;

    public List<CongestionDemo> getCongestionDemos(Long gymId) {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        return repo.findAllByTimeZoneAndClimbingGym_Id(hour, gymId);
    }
    public Optional<CongestionDemoDto> getCongestionDemo(int hour, Long gymId) {
        return repo.findByTimeZoneAndClimbingGym_Id(hour, gymId);
    }
}
