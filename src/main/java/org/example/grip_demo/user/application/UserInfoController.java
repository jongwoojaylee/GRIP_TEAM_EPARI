package org.example.grip_demo.user.application;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final UserService userService;

    @GetMapping("/userinfo/{userid}")
    public String showUserInfo(@PathVariable("userid") Long userid,
                               HttpServletResponse response,
                               Model model) {
        User user = userService.findUserById(userid).orElse(null);

        if (user == null) {
            System.out.println("user null");
            return "redirect:/main";
        }
        String updateurl = "/updateuserform/" + userid;

        model.addAttribute("user", user);
        model.addAttribute("updateurl", updateurl);

        return "user/info";
    }

    @GetMapping("/updateuserform/{userid}")
    public String showUpdateUserForm(@PathVariable Long userid,
                                     Model model) {
        User user = userService.findUserById(userid).orElse(null);
        if (user == null) {
            return "redirect:/main";
        }
        model.addAttribute("user", user);
        return "user/updateUserForm";
    }
    @PostMapping("/updateuser")
    public String processUpdate (@ModelAttribute User user){
        userService.updateUser(user);
        return "redirect:/main";
    }
}
