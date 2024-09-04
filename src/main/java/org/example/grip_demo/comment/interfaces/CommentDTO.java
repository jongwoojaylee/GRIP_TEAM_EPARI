package org.example.grip_demo.comment.interfaces;

import lombok.*;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
public class CommentDTO {

    private Long id;
    private String commentText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Long userId;
    private Long postId;

}


