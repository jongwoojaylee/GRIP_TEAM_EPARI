package org.example.grip_demo.like.interfaces;

import lombok.Getter;

@Getter
public class LikeDTO {
    private Long postId;
    private Long userId;

    public LikeDTO(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}
