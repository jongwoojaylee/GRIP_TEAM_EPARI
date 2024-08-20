package org.example.grip_demo.user.application;

import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.infrastructure.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;

    @GetMapping("/welcomejay")
    public String welcome(Model model,
                          HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Long userId = jwtTokenizer.getUserIdFromCookie(request);
        model.addAttribute("userId", userId);
        return "user/jayhome";
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResponseEntity<Gson> user(){
        User user = userRepository.findById(1L).orElse(null);
        Gson gson = new Gson();
        gson.toJson(user);
        return new ResponseEntity<>(gson, HttpStatus.OK);    }
}
