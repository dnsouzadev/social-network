package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.UserDetailsAdminDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.service.AdminService;
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
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserDetailsAdminDto user) {
        adminService.signup(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change/{id}")
    public ResponseEntity<Void> changeType(@PathVariable Long id) {
        userService.change(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friends/{username}")
    public ResponseEntity<List<UserResponseDto>> listFriends(@PathVariable String username) {
        return ResponseEntity.ok(friendshipService.listFriends(username));
    }
}
