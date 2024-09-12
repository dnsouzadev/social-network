package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.service.FriendshipService;
import com.dnsouzadev.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService service;

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/change/{id}")
    public ResponseEntity<Void> changeType(@PathVariable Long id) {
        service.change(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listFriends/{username}")
    public ResponseEntity<List<UserResponseDto>> listFriends(@PathVariable String username) {
        return ResponseEntity.ok(friendshipService.listFriends(username));
    }
}
