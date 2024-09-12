package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateFriendRequestDto;
import com.dnsouzadev.social_network.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/send")
    public ResponseEntity<?> sendFriendRequest(@RequestBody CreateFriendRequestDto friendRequestDto) {
        friendRequestService.sendFriendRequest(friendRequestDto.sender(), friendRequestDto.receiver());
        return ResponseEntity.ok("Friend request sent successfully!");
    }
}
