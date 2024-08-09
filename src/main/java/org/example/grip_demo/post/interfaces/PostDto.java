package org.example.grip_demo.post.interfaces;

import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.comment.interfaces.CommentDTO;
import org.example.grip_demo.user.domain.User;

import java.util.List;

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

    private List<CommentDTO> comments;
}
