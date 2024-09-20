package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.domain.model.Like;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.repository.FriendshipRepository;
import com.dnsouzadev.social_network.repository.LikeRepository;
import com.dnsouzadev.social_network.repository.PostRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void likePost(String username, Long postId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean isLiked = likeRepository.existsByUserAndPost(user, post);
        boolean isPrivateUser = userRepository.existsByUsernameAndIsHidden(username);
        boolean isFriend = friendshipRepository.existsByUsernameAndFriend(username, post.getUser().getUsername());

        if (isPrivateUser && !isFriend)
            throw new RuntimeException("you are not allowed to like this post");

        if (isLiked)
            likeRepository.deleteLikeByPostId(postId);
        else
            likeRepository.save(new Like(user, post));
    }
}
