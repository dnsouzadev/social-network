package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateCommentDto;
import com.dnsouzadev.social_network.helper.JwtUtil;
import com.dnsouzadev.social_network.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public void createComment(HttpServletRequest request, @RequestBody CreateCommentDto comment) {
        commentService.createComment(jwtUtil.getUsername(request), comment);
    }

    @PutMapping("/update/{commentId}")
    public void updateComment(HttpServletRequest request, @RequestBody CreateCommentDto comment, @PathVariable Long commentId) {
        commentService.updateComment(jwtUtil.getUsername(request), comment, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(HttpServletRequest request, @PathVariable Long commentId) {
        commentService.deleteComment(jwtUtil.getUsername(request), commentId);
    }
}
