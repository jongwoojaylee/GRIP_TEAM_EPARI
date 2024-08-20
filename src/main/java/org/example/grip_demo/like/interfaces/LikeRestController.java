package org.example.grip_demo.like.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.like.application.LikeService;
import org.example.grip_demo.like.domain.Like;
import org.example.grip_demo.post.application.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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

    @GetMapping("/api/like")
    public Like like(@RequestParam("postId") Long postId, HttpServletRequest request) {
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

//        System.out.println(userId);
        Like likeByPostIdAndUserId = likeService.getLikeByPostIdAndUserId(postId, userId);

        if (likeByPostIdAndUserId != null) {
            likeService.deleteLike(likeByPostIdAndUserId);
        } else {
            return likeService.createLike(postId, userId);
        }
        return likeByPostIdAndUserId;
    }
}
