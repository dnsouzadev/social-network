package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.CreateCommentDto;
import com.dnsouzadev.social_network.domain.model.Comment;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PostRepository postRepository;

    public void createComment(String username, CreateCommentDto comment) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(comment.postId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean isPrivateUser = userRepository.existsByUsernameAndIsHidden(username);
        boolean isFriend = friendshipRepository.existsByUsernameAndFriend(username, post.getUser().getUsername());

        if (isPrivateUser && !isFriend)
            throw new RuntimeException("you are not allowed to comment this post");

        commentRepository.save(new Comment(user, post, comment.comment()));
    }
    
    public void updateComment(String username, CreateCommentDto comment, Long commentId) {

        Comment commentToUpdate = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (commentToUpdate.getUser().getUsername().equals(username)) {
            commentToUpdate.setContent(comment.comment());
            commentRepository.save(commentToUpdate);
            return;
        } else {
            throw new RuntimeException("you are not allowed to update this comment");
        }
    }

    public void deleteComment(String username, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getUser().getUsername().equals(username)) {
            commentRepository.deleteById(commentId);
            return;
        } else if (comment.getPost().getUser().getUsername().equals(username)) {
            commentRepository.deleteById(commentId);
            return;
        } else {
            throw new RuntimeException("you are not allowed to delete this comment");
        }
    }

}
