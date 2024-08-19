package org.example.grip_demo.like.domain;

import jakarta.persistence.*;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;

@Entity(name = "USER_POST_LIKES")
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

    public void setUser(User user) {
        this.user = user;
    }
}
