package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.*;
import com.dnsouzadev.social_network.helper.JwtUtil;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.helper.ResponseModel;
import com.dnsouzadev.social_network.service.FriendRequestService;
import com.dnsouzadev.social_network.service.FriendshipService;
import com.dnsouzadev.social_network.service.PostService;
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
    private JwtUtil jwtUtil;

    @Autowired
    private ResponseModel responseModel;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private PostService postService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserDetailsDto user) {
        service.signup(user);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto user) {
        String jwt = service.login(user);
        return responseModel.sendJwtResponse(jwt);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(HttpServletRequest request) {
        var username = jwtUtil.getUsername(request);
        User profile = service.findByUsername(username);
        List<PostDto> posts = postService.listPostsByUser(profile);
        ListPostsDto postsDto = new ListPostsDto(posts);
        return ResponseEntity.ok(new ProfileResponseDto(profile, postsDto));


    }

    @GetMapping("/friends")
    public ResponseEntity<List<UserResponseDto>> getFriends(HttpServletRequest request) {
        return ResponseEntity.ok(friendshipService.listFriends(jwtUtil.getUsername(request)));
    }

    @GetMapping("/public")
    public ResponseEntity<List<UserResponseDto>> findAllPublicUsers() {
        return ResponseEntity.ok(service.findAllPublicUsers());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestResponseDto>> getFriendsRequests(HttpServletRequest request) {
        return ResponseEntity.ok(friendRequestService.getFriendRequests(jwtUtil.getUsername(request)));
    }

    @GetMapping("/change")
    public ResponseEntity<Void> changeTypeUser(HttpServletRequest request) {
        var usernameLogged = jwtUtil.getUsername(request);
        User userLogged = service.findByUsername(usernameLogged);
        service.change(userLogged.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete/{username}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable String username, HttpServletRequest request) {
        String sender = jwtUtil.getUsername(request);
        friendshipService.deleteFriendship(sender, username);
        return ResponseEntity.noContent().build();
    }
}
