package org.example.grip_demo.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.like.domain.Like;
import org.example.grip_demo.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Title", length = 255, nullable = true)
    private String title;

    @Column(name = "Content", length = 500, nullable = true)
    private String content;

    @Column(name = "Created_At", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "Updated_At", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name = "View_Count", nullable = true)
    private int viewCount;

    @Column(name = "Like_Count", nullable = true)
    private int likeCount;


    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "CLIMBING_GYM_ID", nullable = true)
    private ClimbingGym climbingGym;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdAt")
    private List<Comment> comments;

//    @OneToMany(mappedBy = "post")
//    private Set<Like> likes;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser_id(User user) {
        this.user = user;
    }

    public void setClimbingGymId(ClimbingGym climbingGym) {
        this.climbingGym = climbingGym;
    }

}

