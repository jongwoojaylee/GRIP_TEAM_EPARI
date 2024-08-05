package org.example.grip_demo.user.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserService userService;

    @GetMapping("/registeruserform")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "user/registerForm";
    }

    @PostMapping("/registeruser")
    public String processRegisterForm(@ModelAttribute User user) {
        userService.createUser(user);
        return "user/loginform";
    }
}
