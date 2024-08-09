package org.example.grip_demo.user.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.demo.JwtTokenizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/welcomejay")
    public String welcome(Model model,
                          HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    String accessToken = cookie.getValue();
                    Long userId = jwtTokenizer.getUserIdFromToken(accessToken);
                    model.addAttribute("userId", userId);
                }
            }
        }
        return "user/jayhome";
    }
}
