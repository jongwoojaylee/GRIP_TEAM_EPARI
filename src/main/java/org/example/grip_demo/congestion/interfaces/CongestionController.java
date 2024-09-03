package org.example.grip_demo.congestion.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class CongestionController {

    @GetMapping("/bargraph")
    public String bargraph() {
        return "bargraph";
    }
}
