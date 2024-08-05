package org.example.grip_demo.post.interfaces;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.post.application.PostService;
import org.example.grip_demo.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/postlist")
    public String getPostList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts/postlist";
    }

    @GetMapping("/postform")
    public String getPostForm(@RequestParam(value="climbingid",required = false) Long climbingGymId, Model model,
                              RedirectAttributes redirectAttributes){
        try {
            if(climbingGymId == null){
                climbingGymId=9999L;
            }
            model.addAttribute("climbingGymId", climbingGymId);
            return "posts/postform";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "잘못된 요청입니다.");
            return "redirect:/main";
        }
    }

    @PostMapping("/post")
    public String createPost(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("climbingid") Long climbingGymId,
                             @RequestParam("userid") String userid,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes){

        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "인증되지 않은 사용자입니다.");
            return "redirect:/loginform";
        }

        try {
            PostDto postDto = new PostDto();
            postDto.setTitle(title);
            postDto.setContent(content);
            postDto.setClimbingGymId(climbingGymId);
            postDto.setUsername(userid);//userid가 username임

            Post post = mapToEntity(postDto);
            Post createdPost = postService.createPost(post);
            return "redirect:/posts/" + createdPost.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "입력 값 오류가 발생했습니다.");
            return "redirect:/main";
        }
    }

    @GetMapping("/updatepostform")
    public String getUpdatePostForm(@RequestParam("postid") Long postId, Model model, RedirectAttributes redirectAttributes){
        Optional<Post> postOptional = postService.getPostById(postId);
        if(postOptional.isPresent()){
            model.addAttribute("post", mapToDto(postOptional.get()));
            return "posts/updatepostform";
        }else {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            return "redirect:/main";
        }
    }
    // 게시글 수정 과정
    @PostMapping("/updatepost/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             RedirectAttributes redirectAttributes) {
        try {
            Optional<Post> postOptional = postService.getPostById(postId);
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                post.setTitle(title);
                post.setContent(content);

                postService.updatePost(post);
                return "redirect:/posts/" + postId;
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
                return "redirect:/main";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "입력 값 오류가 발생했습니다.");
            return "redirect:/main";
        }
    }

    @DeleteMapping("/deletepost/{postId}")
    public String deletePost(@PathVariable("postId") Long postId, RedirectAttributes redirectAttributes) {
        try {
            postService.deletePost(postId);
            return "redirect:/main";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            return "redirect:/main";
        }
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setViewCount(0);
        post.setLikeCount(0);
        return post;
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setViewCount(post.getView_Count());
        postDto.setLikeCount(post.getLike_Count());
        return postDto;
    }

    @GetMapping("/testtest")
    public String test(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            //redirectAttributes.addFlashAttribute("errorMessage", "인증되지 않은 사용자입니다.");
            return "redirect:/login";
        }
        String token = authorizationHeader.substring(7);

        return "posts/test";
    }


}
