package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateFriendRequestDto;
import com.dnsouzadev.social_network.helper.JwtUtil;
import com.dnsouzadev.social_network.helper.ResponseModel;
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

    @Autowired
    private ResponseModel responseModel;

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestBody CreateFriendRequestDto friendRequestDto, HttpServletRequest request) {
        String usernameSender = jwtUtil.getUsername(request);
        friendRequestService.sendFriendRequest(usernameSender, friendRequestDto.receiver());
        return responseModel.sendResponse("Friend request sent successfully!");
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long id, HttpServletRequest request) {
        String usernameReceiver = jwtUtil.getUsername(request);
        friendRequestService.acceptFriendRequest(id, usernameReceiver);
        return responseModel.sendResponse("Friend request accepted successfully!");
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long id, HttpServletRequest request) {
        String usernameReceiver = jwtUtil.getUsername(request);
        friendRequestService.rejectFriendRequest(id,usernameReceiver);
        return responseModel.sendResponse("Friend request rejected successfully!");
    }

}
