package org.example.grip_demo.post.interfaces;

import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.user.domain.User;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private ClimbingGym climbingGym;

    private User user;
}
