package org.example.grip_demo.like.application;

import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.like.domain.Like;
import org.example.grip_demo.like.domain.LikeDomainService;
import org.example.grip_demo.post.application.PostService;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LikeService {
    private final LikeDomainService likeDomainService;
    private final JwtTokenizer jwtTokenizer;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public LikeService(LikeDomainService likeDomainService, JwtTokenizer jwtTokenizer, UserService userService, PostService postService) {
        this.likeDomainService = likeDomainService;
        this.jwtTokenizer = jwtTokenizer;
        this.userService = userService;
        this.postService = postService;
    }

    public Like createLike(Long postId, Long userId) {
        Like like = new Like();
        Optional<User> userById = userService.findUserById(userId);
        Optional<Post> postById = postService.getPostById(postId);

        like.setUser(userById.get());
        like.setPost(postById.get());

        return likeDomainService.addLike(like);
    }

    public void deleteLike(Like like) {
        likeDomainService.deleteLike(like);
    }

    public Like getLikeByPostIdAndUserId(Long postId, Long userId) {
        return likeDomainService.getLikeByPostIdAndUserId(postId, userId);
    }

    public List<Like> getLikesByPostId(Long postId) {
        return likeDomainService.getLikeByPostId(postId);
    }
}
