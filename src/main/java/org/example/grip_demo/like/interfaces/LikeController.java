package org.example.grip_demo.like.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.grip_demo.demo.JwtTokenizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class LikeController {
    private final JwtTokenizer jwtTokenizer;

    public LikeController(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @GetMapping("/like")
    public String like(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                String accessToken = accessTokenCookie.get().getValue();
                Claims claims = jwtTokenizer.parseAccessToken(accessToken);

                if (claims != null) {
                    Long userId = claims.get("userId", Long.class);
                    String username = claims.get("username", String.class);
                    String name = claims.get("name", String.class);

                    model.addAttribute("userId", userId);
                    model.addAttribute("username", username);
                    model.addAttribute("name", name);
                }

            }
        }
        return "like/like";
    }
}
