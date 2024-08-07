package org.example.grip_demo.user.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.demo.Oauth2Util;
import org.example.grip_demo.user.domain.Role;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserLoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    @GetMapping("/loginform")
    public String showLoginForm(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUri;
        model.addAttribute("link", location);
        return "user/loginform";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpServletResponse response){
        User user =  userService.findUserByUsername(username).orElse(null);
        if(user == null){
            return "redirect:/loginform";
        }
        if(!passwordEncoder.matches(password,user.getPassword())){
            return "redirect:/loginform";
        }

        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        String accessToken = jwtTokenizer.createAccessToken(
                user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(
                user.getId(), user.getEmail(), user.getName(), user.getUsername(), roles);

        //쿠키 생성 후 쿠키 전달
        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
        accessTokenCookie.setHttpOnly(true);  //보안 (쿠키값을 자바스크립트같은곳에서는 접근할수 없어요.)
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.ACCESS_TOKEN_EXPIRE_COUNT/1000)); //30분 쿠키의 유지시간 단위는 초 ,  JWT의 시간단위는 밀리세컨드

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.REFRESH_TOKEN_EXPIRE_COUNT/1000)); //7일

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);



        return "redirect:/welcomejay";
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        // accessToken, refreshToken 삭제
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setHttpOnly(true);
        accessToken.setPath("/");
        accessToken.setMaxAge(0);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setHttpOnly(true);
        refreshToken.setPath("/");
        refreshToken.setMaxAge(0);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        // loginform으로 리다이렉트
        response.sendRedirect("/loginform");
    }
}
