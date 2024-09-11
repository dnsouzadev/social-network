package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.UserDetailsDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserDetailsDto user) {
        service.signup(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDetailsDto user) {
        service.login(user);
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/findPublic")
    public ResponseEntity<List<UserResponseDto>> findAllPublicUsers() {
        return ResponseEntity.ok(service.findAllPublicUsers());
    }

}
