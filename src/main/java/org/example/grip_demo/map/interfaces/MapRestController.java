package org.example.grip_demo.map.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.map.application.SearchApiService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MapRestController {
    private final SearchApiService searchApiService;

    public MapRestController(SearchApiService naverApiService) {
        this.searchApiService = naverApiService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return searchApiService.searchLocal(query);
    }
}
