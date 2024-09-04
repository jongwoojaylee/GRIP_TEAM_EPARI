package org.example.grip_demo.like.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;

@Entity(name = "USER_POST_LIKES")
@Setter
@Getter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;
}
