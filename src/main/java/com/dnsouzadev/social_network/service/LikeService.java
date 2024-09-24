package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.domain.model.Like;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.repository.LikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendshipService friendshipService;

    public void likePost(String username, Long postId) {
        User user = userService.findByUsername(username);
        Post post = postService.findById(postId);

        boolean canLikePost = canLike(user, post);

        if (!canLikePost) throw new RuntimeException("you are not allowed to like this post");

        boolean isLiked = likeRepository.existsByUserAndPost(user, post);

        if (isLiked)
            likeRepository.deleteLikeByPostId(postId);
        else
            likePost(user, post);
    }

    private boolean canLike(User user, Post post) {
        boolean isPrivateUser = userService.existsByUsernameAndIsHidden(post.getUser().getUsername());
        boolean isFriend = friendshipService.existsByUsernameAndFriend(user.getUsername(), post.getUser().getUsername());

        return !isPrivateUser || isFriend;
    }

    private void likePost(User user, Post post) {
        new Like(user, post);
    }
}
