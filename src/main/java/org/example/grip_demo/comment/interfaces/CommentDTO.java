package org.example.grip_demo.comment.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private String commentText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private Post post;

    public Comment toEntity(){
        Comment comments = Comment.builder()
                .id(id)
                .commentText(commentText)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .user_id(user)
                .post_id(post)
                .build();
        return comments;
    }

}
