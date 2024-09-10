package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return service.findAll();
    }

    @PostMapping("/change/{id}")
    public ResponseEntity<Void> changeType(@PathVariable Long id) {
        return service.change(id);
    }
}
