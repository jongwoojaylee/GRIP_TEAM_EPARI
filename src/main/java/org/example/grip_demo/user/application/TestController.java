package org.example.grip_demo.user.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.user.domain.RedisRefreshToken;
import org.example.grip_demo.user.infrastructure.RedisRefreshTokenRepository;
import org.example.grip_demo.user.infrastructure.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @GetMapping("/welcomejay")
    public String welcome(Model model,
                          HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Long userId = jwtTokenizer.getUserIdFromCookie(request);
        model.addAttribute("userId", userId);
        return "user/jayhome";
    }

//    @GetMapping("/getUserInfo")
//    @ResponseBody
//    public ResponseEntity<RedisRefreshToken> token(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("refreshToken".equals(cookie.getName())) {
//                    System.out.println(cookie.getValue());
//                    RedisRefreshToken test = redisRefreshTokenRepository.findById(1L).orElse(null);
//                    System.out.println(test.getRefreshToken());
//                }
//            }
//        }
//
//
//        return new ResponseEntity<>(null, HttpStatus.OK);    }
}
