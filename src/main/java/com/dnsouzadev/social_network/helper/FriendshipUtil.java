package com.dnsouzadev.social_network.helper;

import com.dnsouzadev.social_network.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FriendshipUtil {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public boolean checkFriendship(String username, String friend) {
        // Check if username and friend are friends
        return friendshipRepository.existsByUsernameAndFriend(username, friend);
    }

}
