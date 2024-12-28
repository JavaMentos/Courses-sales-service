package ru.home.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.home.courses.dto.CreateUser;
import ru.home.courses.dto.LoginRequest;
import ru.home.courses.dto.UserDTO;
import ru.home.courses.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CreateUser user) {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean success = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        return success ?
                ResponseEntity.ok("Login successful") :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDTO user = userService.getUserByEmail(authentication.getName());
        return ResponseEntity.ok(user);
    }
}
