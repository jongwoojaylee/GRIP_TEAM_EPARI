package org.example.grip_demo.like.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.like.application.LikeService;
import org.example.grip_demo.like.domain.Like;
import org.example.grip_demo.post.application.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class LikeRestController {
    private final LikeService likeService;
    private final PostService postService;
    private final JwtTokenizer jwtTokenizer;

    public LikeRestController(LikeService likeService, PostService postService, JwtTokenizer jwtTokenizer) {
        this.likeService = likeService;
        this.postService = postService;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/api/like")
    public ResponseEntity<Boolean> like(@RequestParam("postId") Long postId, HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        Long userId = null;
        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                String accessToken = accessTokenCookie.get().getValue();
                Claims claims = jwtTokenizer.parseAccessToken(accessToken);

                if (claims != null) {
                    userId = claims.get("userId", Long.class);
                }
            }
        }

        Like likeByPostIdAndUserId = likeService.getLikeByPostIdAndUserId(postId, userId);
        boolean isLiked;
        if (likeByPostIdAndUserId != null) {
            likeService.deleteLike(likeByPostIdAndUserId);
            isLiked = false;
        } else {
            likeService.createLike(postId, userId);
            isLiked = true;
        }
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping("/api/isLike")
    public ResponseEntity<Boolean> isLike(@RequestParam("postId") Long postId, HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        Long userId = null;
        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                String accessToken = accessTokenCookie.get().getValue();
                Claims claims = jwtTokenizer.parseAccessToken(accessToken);

                if (claims != null) {
                    userId = claims.get("userId", Long.class);
                }
            }
        }

        Like likeByPostIdAndUserId = likeService.getLikeByPostIdAndUserId(postId, userId);
        boolean isLike = likeByPostIdAndUserId != null;

        return ResponseEntity.ok(isLike);
    }

    @GetMapping("/api/likecount")
    public ResponseEntity<?> likecount(@RequestParam("postId") Long postId) {
        List<Like> likes = likeService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes.size());
    }
}
