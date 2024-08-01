package org.example.grip_demo.post.interfaces;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private Long userId;
    private Long climbingGymId;
}
