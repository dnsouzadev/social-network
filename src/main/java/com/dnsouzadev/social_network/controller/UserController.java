package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.FriendRequestResponseDto;
import com.dnsouzadev.social_network.dto.UserDetailsDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.helper.GetUserByJwt;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.service.FriendRequestService;
import com.dnsouzadev.social_network.service.FriendshipService;
import com.dnsouzadev.social_network.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private GetUserByJwt getUserByJwt;

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserDetailsDto user) {
        service.signup(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDetailsDto user) {
        String jwt = service.login(user);
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("/friends")
    public ResponseEntity<List<UserResponseDto>> getFriends(HttpServletRequest request) {
        return ResponseEntity.ok(friendshipService.listFriends(getUserByJwt.getUser(request)));
    }

    @GetMapping("/public")
    public ResponseEntity<List<UserResponseDto>> findAllPublicUsers() {
        return ResponseEntity.ok(service.findAllPublicUsers());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestResponseDto>> getFriendsRequests(HttpServletRequest request) {
        return ResponseEntity.ok(friendRequestService.getFriendRequests(getUserByJwt.getUser(request)));
    }

    @PostMapping("/change")
    public ResponseEntity<Void> changeTypeUser(HttpServletRequest request) {
        var usernameLogged = getUserByJwt.getUser(request);
        User userLogged = service.findByUsername(usernameLogged);
        service.change(userLogged.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete/{username}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable String username, HttpServletRequest request) {
        String sender = getUserByJwt.getUser(request);
        friendshipService.deleteFriendship(sender, username);
        return ResponseEntity.ok().build();
    }
}
