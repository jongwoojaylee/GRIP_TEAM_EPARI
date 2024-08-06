package org.example.grip_demo.condemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConDemoService {
    private final CondemoRepo repo;

    public List<CongestionDemo> getCongestionDemos(Long gymId) {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        return repo.findByTimeZoneAndClimbingGym_Id(hour, gymId);
    }
}
