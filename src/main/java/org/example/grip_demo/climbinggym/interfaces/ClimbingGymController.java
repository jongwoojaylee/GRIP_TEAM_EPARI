package org.example.grip_demo.climbinggym.interfaces;

import org.example.grip_demo.climbinggym.application.ClimingGymService;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.post.application.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ClimbingGymController {
    private final ClimingGymService climingGymService;
    private final PostService postService;

    @Autowired
    public ClimbingGymController(ClimingGymService climingGymService, PostService postService) {
        this.climingGymService = climingGymService;
        this.postService = postService;
    }

    @GetMapping("/gymlist")
    public String gymlist(Model model) {
        List<ClimbingGym> climbinggyms = climingGymService.getAllClimbingGyms();
        model.addAttribute("climbinggyms", climbinggyms);
        return "climbinggyms/climbinggymList";
    }

    @GetMapping("/climbinggym")
    public String gym(@RequestParam("climbingid") Long climbingId, Model model) {
        Optional<ClimbingGym> climbinggym = climingGymService.getClimbingGym(climbingId);
        model.addAttribute("climbinggym", climbinggym.get());
//        model.addAttribute("posts", posts);
        return "climbinggyms/climbinggym";
    }


}
