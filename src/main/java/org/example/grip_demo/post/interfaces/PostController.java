package org.example.grip_demo.post.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.post.application.PostService;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final JwtTokenizer jwtTokenizer;

    private final PostService postService;

    @Value("${count.gym.id}")
    private Long gymId;

    @GetMapping("/postlist")
    public String getPostList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts/postlist";
    }

    @GetMapping("/postform")
    public String getPostForm(@RequestParam(value="climbingid",required = false) Long climbingGymId, Model model,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes){

        try {
            if(climbingGymId == null){
                climbingGymId=gymId;
            }

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


            Claims claims=jwtTokenizer.parseAccessToken(token);
            log.info("claims 생성까진 된다? 부럽지?"+claims.toString());

            String name = (String)claims.get("name");
            log.info("이름 뭔데에에엥ㄷ: "+name);

            String userId = claims.get("userId").toString();
            Long longuserId=Long.parseLong(userId);

            model.addAttribute("climbingGymId", climbingGymId);
            model.addAttribute("userId", longuserId);
            model.addAttribute("name", name);
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
                             @RequestParam("userId") Long userid,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes){

        try {
            PostDto postDto = new PostDto();
            postDto.setTitle(title);
            postDto.setContent(content);
            ClimbingGym climbingGym=new ClimbingGym();
            climbingGym.setId(climbingGymId);
            postDto.setClimbingGym(climbingGym);
            User user = new User();
            user.setId(userid);
            postDto.setUser(user);
            Post createdPost = postService.createPost(mapToEntity(postDto));
            return "redirect:/post/" + climbingGymId+"/"+createdPost.getId();
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
            String name =postOptional.get().getUser_id().getName();
            model.addAttribute("postId",postId);
            model.addAttribute("name", name);
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
                return "redirect:/post/"+ post.getClimbingGym_id().getId()+"/"+ postId;
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
            log.info(postId.toString());
            postService.deletePost(postId);
            return "/postlist";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            return "redirect:/main";
        }
    }

    @GetMapping("/post/{climbingid}/{postid}")
    public String postDetail(@PathVariable Long postid,
                             @PathVariable Long climbingid,
                             HttpServletRequest request,
                             Model model,RedirectAttributes redirectAttributes){
        Post post= postService.getPostById(postid).get();
        String name= post.getUser_id().getName();
        String gymId = climbingid.toString();

        //현재 사용중인 사용자 뽑아보리깅
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
        Claims claims=jwtTokenizer.parseAccessToken(token);
        String currentUserName=claims.get("username").toString();

        List<Comment> comments = post.getComments();

        model.addAttribute("post", post);
        model.addAttribute("name",name);
        model.addAttribute("gymId",gymId);
        model.addAttribute("currentUserName",currentUserName);
        model.addAttribute("comments",comments);
        log.info("post가 뭔데에 : "+post.toString());
        return "posts/detail";
    }


    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser_id(postDto.getUser());
        post.setClimbingGymId(postDto.getClimbingGym());
        post.setCreatedAt(LocalDateTime.now());
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


}
