package org.example.grip_demo.climbinggym.application;

import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.climbinggym.domain.ClimbingGymRepository;
import org.example.grip_demo.climbinggym.domain.PageablePostRepository;
import org.example.grip_demo.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClimingGymService {
    private final ClimbingGymRepository climbingGymRepository;
    private final PageablePostRepository pageablePostRepository;

    @Autowired
    public ClimingGymService(ClimbingGymRepository climbingGymRepository, PageablePostRepository pageablePostRepository) {
        this.climbingGymRepository = climbingGymRepository;
        this.pageablePostRepository = pageablePostRepository;
    }

    public ClimbingGym createClimbingGym(ClimbingGym climbingGym) {
        return climbingGymRepository.save(climbingGym);
    }

    public Optional<ClimbingGym> getClimbingGym(Long id) {
        return climbingGymRepository.findById(id);
    }

    public List<ClimbingGym> getAllClimbingGyms() {
        List<ClimbingGym> climbingGyms = climbingGymRepository.findAll();
        // ID가 9999인 ClimbingGym을 제외하고 나머지를 필터링
        return climbingGyms.stream()
                .filter(gym -> gym.getId() != 9999)
                .collect(Collectors.toList());
    }

    public List<ClimbingGym> searchClimbingGyms(String keyword) {
        return climbingGymRepository.findAll().stream()
                .filter(gym -> gym.getName().contains(keyword) || gym.getAddress().contains(keyword))
                .collect(Collectors.toList());
    }

    public void deleteClimbingGym(Long id) {
        climbingGymRepository.deleteById(id);
    }

    public Page<Post> pageClimbingGymPosts(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> byPostPage = pageablePostRepository.findByClimbingGym_Id(id, pageable);

        return byPostPage;
    }
}
