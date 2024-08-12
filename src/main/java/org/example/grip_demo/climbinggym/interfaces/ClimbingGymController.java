package org.example.grip_demo.climbinggym.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.grip_demo.climbinggym.application.ClimingGymService;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.post.application.PostService;
import org.example.grip_demo.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ClimbingGymController {
    private final ClimingGymService climingGymService;
    private final PostService postService;
    private final JwtTokenizer jwtTokenizer;

    @Autowired
    public ClimbingGymController(ClimingGymService climingGymService, PostService postService, JwtTokenizer jwtTokenizer) {
        this.climingGymService = climingGymService;
        this.postService = postService;
        this.jwtTokenizer = jwtTokenizer;
    }

    @GetMapping("/gymlist")
    public String gymlist(Model model) {
        List<ClimbingGym> climbinggyms = climingGymService.getAllClimbingGyms();
        model.addAttribute("climbinggyms", climbinggyms);
        return "climbinggyms/climbinggymList";
    }

    @GetMapping("/climbinggym")
    public String gym(HttpServletRequest request, @RequestParam("climbingid") Long climbingId, Model model) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst();

            if (accessTokenCookie.isPresent()) {
                String accessToken = accessTokenCookie.get().getValue();
                Claims claims = jwtTokenizer.parseAccessToken(accessToken);

                if (claims != null) {
                    String username = claims.get("username", String.class);
                    String name = claims.get("name", String.class);

                    model.addAttribute("username", username);
                    model.addAttribute("name", name);
                }

            }
        }
        Optional<ClimbingGym> climbinggym = climingGymService.getClimbingGym(climbingId);
        model.addAttribute("climbinggym", climbinggym.get());
        List<Post> posts = climbinggym.get().getPosts();
        model.addAttribute("posts", posts);
        return "climbinggyms/climbinggym";
    }


}
