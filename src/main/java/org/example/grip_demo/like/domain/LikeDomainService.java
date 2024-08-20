package org.example.grip_demo.like.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeDomainService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeDomainService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like addLike(Like like) {
        return likeRepository.save(like);
    }
    public void deleteLike(Like like) {
        likeRepository.delete(like);
    }
    public void deleteLikeById(Long id) {
        likeRepository.deleteById(id);
    }
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }
    public List<Like> getLikeByPostId(Long id) {
        return likeRepository.findAllByPostId(id);
    }
    public Like getLikeByPostIdAndUserId(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId);
    }
}
