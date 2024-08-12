package org.example.grip_demo.map.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.demo.JwtTokenizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MapController {
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                String accessToken = accessTokenCookie.get().getValue();
                Claims claims = jwtTokenizer.parseAccessToken(accessToken);

                if (claims != null) {
                    Long userId = jwtTokenizer.getUserIdFromToken(accessToken);
                    String username = claims.get("username", String.class);
                    String name = claims.get("name", String.class);

                    model.addAttribute("userId", userId);
                    model.addAttribute("username", username);
                    model.addAttribute("name", name);
                }

            }
        }
        return "main";
    }
}
