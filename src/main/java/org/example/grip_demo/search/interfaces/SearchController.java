package org.example.grip_demo.search.interfaces;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.climbinggym.domain.ClimbingGym;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.search.application.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;


    @GetMapping("/searchform")
    public String showSearchForm(Model model) {
        return "search/searchform";
    }

    @GetMapping("/search")
    public String processSearch(@RequestParam(value = "query", required = false) String query,
                                @RequestParam(value = "postPage", defaultValue = "0") int postPage,
                                @RequestParam(value = "climbingGymPage", defaultValue = "0") int climbingGymPage,
                                Model model){
        int pageSize = 5;
        model.addAttribute("query", query);

        Page<Post> posts = searchService.searchPostContaining(query,postPage,pageSize);
        model.addAttribute("posts", posts);
        model.addAttribute("totalPostPages", posts.getTotalPages());
        model.addAttribute("currentPostPage", postPage);

        Page<ClimbingGym> climbingGyms = searchService.searchClimbingGymContaining(query,climbingGymPage,pageSize);
        model.addAttribute("climbingGyms", climbingGyms);
        model.addAttribute("totalClimbingGymsPages", climbingGyms.getTotalPages());
        model.addAttribute("currentClimbingGymsPage", climbingGymPage);

        return "search/searchform";

    }



}
