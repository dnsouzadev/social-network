package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.CreateCommentDto;
import com.dnsouzadev.social_network.helper.GetUserByJwt;
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
    private GetUserByJwt getUserByJwt;

    @PostMapping
    public void createComment(HttpServletRequest request, @RequestBody CreateCommentDto comment) {
        commentService.createComment(getUserByJwt.getUser(request), comment);
    }

    @PutMapping("/update/{commentId}")
    public void updateComment(HttpServletRequest request, @RequestBody CreateCommentDto comment, @PathVariable Long commentId) {
        commentService.updateComment(getUserByJwt.getUser(request), comment, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(HttpServletRequest request, @PathVariable Long commentId) {
        commentService.deleteComment(getUserByJwt.getUser(request), commentId);
    }
}
