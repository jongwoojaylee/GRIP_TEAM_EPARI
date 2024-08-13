package org.example.grip_demo.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", length = 100)
    private String commentText;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
