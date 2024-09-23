package com.orca.resources;

import com.orca.dto.UserRegistrationDTO;
import com.orca.model.User;
import com.orca.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistrationResource {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDTO) {
        User user = new User();
        user.setName(registrationDTO.getName());
        user.setLogin(registrationDTO.getUsername());
        user.setPassword(registrationDTO.getPassword());
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}