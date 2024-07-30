package org.example.grip_demo.map.interfaces;

import org.example.grip_demo.map.application.MapApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapRestController {
    private final MapApiService naverApiService;

    public MapRestController(MapApiService naverApiService) {
        this.naverApiService = naverApiService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return naverApiService.searchLocal(query);
    }
}
