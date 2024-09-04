package org.example.grip_demo.climbinggym.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClimbingGymDomainService {
    private final ClimbingGymRepository climbingGymRepository;

    @Autowired
    public ClimbingGymDomainService(ClimbingGymRepository climbingGymRepository) {
        this.climbingGymRepository = climbingGymRepository;
    }

    public ClimbingGym createClimbingGym(ClimbingGym climbingGym) {
        return climbingGymRepository.save(climbingGym);
    }

    public Optional<ClimbingGym> getClimbingGym(Long id) {
        return climbingGymRepository.findById(id);
    }

    public List<ClimbingGym> getAllClimbingGyms() {
        return climbingGymRepository.findAll();
    }

    public void deleteClimbingGym(Long id) {
        climbingGymRepository.deleteById(id);
    }


}
