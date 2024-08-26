package org.example.grip_demo.user.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.RegisterUserDTO;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserService userService;

    @GetMapping("/registeruserform")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new RegisterUserDTO());
        return "user/registerForm";
    }

    @PostMapping("/registeruser")
    public String processRegisterForm(@Valid RegisterUserDTO registerUserDTO,
                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("validation error");
            return "redirect:/registeruserform";
        }
        //userDTO-> user로 변환
        User user = userService.registerUserDTOToUser(registerUserDTO);
        userService.createUser(user);
        return "redirect:/loginform";
    }

    @GetMapping("/registeruserform/kakao")
    public String showKakaoRegisterForm(Model model,
                                        @RequestParam String username,
                                        @RequestParam String name) {
        User user = new User();
        user.setUsername("kakao_"+username);
        user.setName(name);
        user.setPassword(username+name);
        model.addAttribute("user", user);
        return "user/kakaoRegisterForm";
    }

}
