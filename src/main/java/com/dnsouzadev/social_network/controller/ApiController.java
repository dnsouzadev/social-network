package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateFriendRequestDto;
import com.dnsouzadev.social_network.helper.JwtUtil;
import com.dnsouzadev.social_network.helper.ResponseMessage;
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
    private JwtUtil jwtUtil;


    @PostMapping("/send")
    public ResponseEntity<ResponseMessage> sendFriendRequest(@RequestBody CreateFriendRequestDto friendRequestDto, HttpServletRequest request) {
        String usernameSender = jwtUtil.getUsername(request);
        friendRequestService.sendFriendRequest(usernameSender, friendRequestDto.receiver());
        return ResponseEntity.ok(new ResponseMessage("Friend request sent successfully!"));
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<ResponseMessage> acceptFriendRequest(@PathVariable Long id, HttpServletRequest request) {
        String usernameReceiver = jwtUtil.getUsername(request);
        friendRequestService.acceptFriendRequest(id, usernameReceiver);
        return ResponseEntity.ok(new ResponseMessage("Friend request accepted successfully!"));
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<ResponseMessage> rejectFriendRequest(@PathVariable Long id, HttpServletRequest request) {
        String usernameReceiver = jwtUtil.getUsername(request);
        friendRequestService.rejectFriendRequest(id,usernameReceiver);
        return ResponseEntity.ok(new ResponseMessage("Friend request rejected successfully!"));
    }

}
