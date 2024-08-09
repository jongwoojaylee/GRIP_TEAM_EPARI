package org.example.grip_demo.search.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.search.infrastructure.SearchClimbingGymRepository;
import org.example.grip_demo.search.infrastructure.SearchPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchPostRepository searchPostRepository;
    private final SearchClimbingGymRepository searchClimbingGymRepository;

    public Page<Post> searchPostContaining(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = searchPostRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        return posts;
    }

    public Page<ClimbingGym> searchClimbingGymContaining(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return searchClimbingGymRepository.findByNameContaining(keyword, pageable);
    }

}
