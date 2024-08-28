package org.example.grip_demo.user.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final UserService userService;
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/userinfo/{userid}")
    public String showUserInfo(@PathVariable("userid") Long userId,
                               @RequestParam(value = "postPage", defaultValue = "0") int postPage,
                               @RequestParam(value = "commentPage", defaultValue = "0") int commentPage,
                               HttpServletRequest request,
                               Model model) {
        User user = userService.findUserById(userId).orElse(null);
        Long id = jwtTokenizer.getUserIdFromCookie(request);

        if (user == null || !user.getId().equals(id)) {
            return "redirect:/main";
        }

        String updateurl = "/updateuserform/" + userId;

        model.addAttribute("user", user);
        model.addAttribute("updateurl", updateurl);

        int pageSize = 5;
        Page<Post> posts = userService.findPostByUserId(userId, postPage, pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("totalPostPages", posts.getTotalPages());
        model.addAttribute("currentPostPage", postPage);

        Page<Comment> comments = userService.findCommentByUserId(userId, commentPage, pageSize);

        model.addAttribute("comments", comments);
        model.addAttribute("totalCommentPages", comments.getTotalPages());
        model.addAttribute("currentCommentPage", commentPage);

        return "user/info";
    }

    @GetMapping("/updateuserform/{userid}")
    public String showUpdateUserForm(@PathVariable Long userid,
                                     Model model,
                                     HttpServletRequest request) {
        User user = userService.findUserById(userid).orElse(null);
        if (user == null) {
            return "redirect:/main";
        }
        Long id = jwtTokenizer.getUserIdFromCookie(request);
        if(!user.getId().equals(id)) {
            return "redirect:/main";
        }
        model.addAttribute("user", user);
        return "user/updateUserForm";
    }

    @PostMapping("/updateuser")
    public String processUpdate (@ModelAttribute User user){
        User updatedUser = userService.updateUser(user);
        return "redirect:/userinfo/"+updatedUser.getId();
    }
}
