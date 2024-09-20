package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreatePostDto;
import com.dnsouzadev.social_network.dto.PostDto;
import com.dnsouzadev.social_network.helper.JwtUtil;
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
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Void> createPost(HttpServletRequest request, @RequestBody CreatePostDto post) {
        var username = jwtUtil.getUsername(request);
        postService.createPost(username, post);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(HttpServletRequest request, @PathVariable Long id) {
        var username = jwtUtil.getUsername(request);
        return ResponseEntity.ok(postService.getPost(username, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(HttpServletRequest request, @PathVariable Long id) {
        var username = jwtUtil.getUsername(request);
        postService.deletePost(username, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(HttpServletRequest request, @PathVariable Long id, @RequestBody CreatePostDto post) {
        var username = jwtUtil.getUsername(request);
        postService.updatePost(username, id, post);
        return ResponseEntity.ok().build();
    }

}
