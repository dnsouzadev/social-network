package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.CreateCommentDto;
import com.dnsouzadev.social_network.domain.model.Comment;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendshipService friendshipService;

    public void createComment(String username, CreateCommentDto comment) {

        User user = userService.findByUsername(username);
        Post post = postService.findById(comment.postId());

        boolean isPrivateUser = userService.existsByUsernameAndIsHidden(username);
        boolean isFriend = friendshipService.existsByUsernameAndFriend(username, post.getUser().getUsername());

        if (isPrivateUser && !isFriend)
            throw new RuntimeException("you are not allowed to comment this post");

        commentRepository.save(createComment(user, post, comment));
    }

    private Comment createComment(User user, Post post, CreateCommentDto comment) {
        return new Comment(user, post, comment.comment());
    }
    
    public void updateComment(String username, CreateCommentDto comment, Long commentId) {

        Comment commentToUpdate = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (commentToUpdate.getUser().getUsername().equals(username)) {
            commentToUpdate.setContent(comment.comment());
            commentRepository.save(commentToUpdate);
        } else {
            throw new RuntimeException("you are not allowed to update this comment");
        }
    }

    public void deleteComment(String username, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getUser().getUsername().equals(username)) {
            deleteCommentById(commentId);
        } else if (comment.getPost().getUser().getUsername().equals(username)) {
            deleteCommentById(commentId);
        } else {
            throw new RuntimeException("you are not allowed to delete this comment");
        }
    }

    private void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
