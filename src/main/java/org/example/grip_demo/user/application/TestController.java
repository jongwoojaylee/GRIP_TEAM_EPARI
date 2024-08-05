package org.example.grip_demo.user.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/welcomejay")
    public String welcome() {
        return "user/jayhome";
    }
}
