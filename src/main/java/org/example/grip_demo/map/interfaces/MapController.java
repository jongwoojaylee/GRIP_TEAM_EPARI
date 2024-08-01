package org.example.grip_demo.map.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
