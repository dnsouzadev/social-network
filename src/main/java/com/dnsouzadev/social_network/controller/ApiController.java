package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateFriendRequestDto;
import com.dnsouzadev.social_network.helper.GetUserByJwt;
import com.dnsouzadev.social_network.service.FriendRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private GetUserByJwt getUserByJwt;

    @PostMapping("/send")
    public ResponseEntity<?> sendFriendRequest(@RequestBody CreateFriendRequestDto friendRequestDto, HttpServletRequest request) {
        String usernameSender = getUserByJwt.getUser(request);
        friendRequestService.sendFriendRequest(usernameSender, friendRequestDto.receiver());
        return ResponseEntity.ok("Friend request sent successfully!");
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long id, HttpServletRequest request) {
        String usernameReceiver = getUserByJwt.getUser(request);
        friendRequestService.acceptFriendRequest(id, usernameReceiver);
        return ResponseEntity.ok("Friend request accepted successfully!");
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long id, HttpServletRequest request) {
        String usernameReceiver = getUserByJwt.getUser(request);
        friendRequestService.rejectFriendRequest(id,usernameReceiver);
        return ResponseEntity.ok("Friend request rejected successfully!");
    }

}
