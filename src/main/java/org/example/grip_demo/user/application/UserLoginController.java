package org.example.grip_demo.user.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.user.domain.RedisRefreshToken;
import org.example.grip_demo.user.domain.Role;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.infrastructure.RedisRefreshTokenRepository;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

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
                               HttpServletResponse response,
                               Model model){
        //kakao user의 일반 로그인 방지
        if(username.startsWith("kakao")) {
            model.addAttribute("error", "로그인에 실패하였습니다");
            return "redirect:/loginform";
        }

        User user =  userService.findUserByUsername(username).orElse(null);

        if(user == null){
            model.addAttribute("error", "로그인에 실패하였습니다");
            return "redirect:/loginform";
        }
        if(!passwordEncoder.matches(password,user.getPassword())){
            model.addAttribute("error", "로그인에 실패하였습니다");
            return "redirect:/loginform";
        }

        List<String> roles = user.getRoles().stream().map(Role::getName).toList();

        String accessToken = jwtTokenizer.createAccessToken(
                user.getId(), user.getEmail(), user.getNickName(), user.getName(), user.getUsername(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(
                user.getId(), user.getEmail(), user.getNickName(), user.getName(), user.getUsername(), roles);

        //쿠키 생성 후 쿠키 전달
        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.ACCESS_TOKEN_EXPIRE_COUNT/1000)); //30분

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(JwtTokenizer.REFRESH_TOKEN_EXPIRE_COUNT/1000)); //7일

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);


        //redis refresh token 저장
        redisRefreshTokenRepository.save(new RedisRefreshToken(user.getId(), refreshToken));




        return "redirect:/main";
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

        // main 페이지로 이동
        response.sendRedirect("/main");

    }

    @GetMapping("/login/help")
    public String showHelp(){
        return "user/helpForm";
    }
}
