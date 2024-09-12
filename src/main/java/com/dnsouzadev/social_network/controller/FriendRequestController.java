package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateFriendRequestDto;
import com.dnsouzadev.social_network.dto.FriendRequestResponseDto;
import com.dnsouzadev.social_network.model.FriendRequest;
import com.dnsouzadev.social_network.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getFriendRequests(@PathVariable String username) {
        List<FriendRequestResponseDto> listFr = friendRequestService.getFriendRequests(username);
        return ResponseEntity.ok(listFr);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long id) {
        friendRequestService.acceptFriendRequest(id);
        return ResponseEntity.ok("Friend request accepted successfully!");
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<?> rejectFriendRequest(@PathVariable Long id) {
        friendRequestService.rejectFriendRequest(id);
        return ResponseEntity.ok("Friend request rejected successfully!");
    }

}
