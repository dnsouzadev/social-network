package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.helper.GetUserByJwt;
import com.dnsouzadev.social_network.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private GetUserByJwt getUserByJwt;

    @GetMapping("/{postId}")
    public void likePost(HttpServletRequest request, @PathVariable Long postId) {
        var username = getUserByJwt.getUser(request);
        likeService.likePost(username, postId);
    }


}
