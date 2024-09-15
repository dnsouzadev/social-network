package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.model.Friendship;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.FriendshipRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDto> listFriends(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(user, user);

        List<UserResponseDto> friends = new ArrayList<>();
        for (Friendship friendship : friendships) {
            if (friendship.getUser1().equals(user)) {
                friends.add(new UserResponseDto(friendship.getUser2().getId(), friendship.getUser2().getUsername(), friendship.getUser2().getTypeAccount()));
            } else {
                friends.add(new UserResponseDto(friendship.getUser1().getId(), friendship.getUser1().getUsername(), friendship.getUser1().getTypeAccount()));
            }
        }


        return friends;
    }
}

