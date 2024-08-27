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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String gym(HttpServletRequest request,
                      @RequestParam("climbingid") Long climbingId,
                      @RequestParam(defaultValue = "0") int page,
                      Model model) {
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
        int pageSize = 9;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Post> postsPage = climingGymService.pageClimbingGymPosts(climbingId, page, pageSize);

        Optional<ClimbingGym> climbinggym = climingGymService.getClimbingGym(climbingId);
        model.addAttribute("climbinggym", climbinggym.get());
//        List<Post> posts = climbinggym.get().getPosts();
//        model.addAttribute("posts", posts);
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        return "climbinggyms/climbinggym";
    }
}
