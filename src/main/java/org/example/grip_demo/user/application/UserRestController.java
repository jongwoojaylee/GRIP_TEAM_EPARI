package org.example.grip_demo.user.application;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/api/check/username")
    public ResponseEntity<Map<String, Boolean>> checkUsernameAvailability(@RequestParam String username) {
        boolean isAvailable = userService.isUsernameExist(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/check/email")
    public ResponseEntity<Map<String, Boolean>> checkUserEmailAvailability(@RequestParam String email) {
        boolean isAvailable = userService.isUserEmailExist(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
