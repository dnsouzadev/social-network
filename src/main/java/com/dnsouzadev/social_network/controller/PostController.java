package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.helper.GetUserByJwt;
import com.dnsouzadev.social_network.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private GetUserByJwt getUserByJwt;

    @PostMapping
    public ResponseEntity<Void> createPost(HttpServletRequest request, @RequestBody CreatePostDto post) {
        var username = getUserByJwt.getUser(request);
        postService.createPost(username, post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(HttpServletRequest request, @PathVariable Long id) {
        var username = getUserByJwt.getUser(request);
        postService.deletePost(username, id);
        return ResponseEntity.ok().build();
    }

}
