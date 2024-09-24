package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.domain.model.Friendship;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.helper.Mapper;
import com.dnsouzadev.social_network.repository.FriendshipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private Mapper mapper;

    public boolean existsByUsernameAndFriend(String username, String friend) {
        return friendshipRepository.existsByUsernameAndFriend(username, friend);
    }

    public void createFriendship(User sender, User receiver) {
        friendshipRepository.save(new Friendship(sender, receiver));
        friendRequestService.deleteFriendRequestBySenderAndReceiver(sender, receiver);
    }

    public List<UserResponseDto> listFriends(String username) {
        User user = userService.findByUsername(username);

        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(user, user);

        List<UserResponseDto> friends = new ArrayList<>();
        for (Friendship friendship : friendships) {
            User friend = friendship.getUser1().equals(user) ? friendship.getUser2() : friendship.getUser1();
            friends.add(mapper.toUserResponseDto(friend));
        }

        return friends;
    }

    public void deleteFriendship(String sender, String receiver) {
        User user1 = userService.findByUsername(sender);

        User user2 = userService.findByUsername(receiver);

        Friendship friendshipExists = checkFriendship(user1, user2);

        if (friendshipExists != null) {
            friendshipRepository.delete(friendshipExists);
            friendRequestService.deleteFriendRequestBySenderAndReceiver(user1, user2);
        } else
            throw new RuntimeException("Friendship does not exist");

    }

    public Friendship checkFriendship(User sender, User receiver) {

        List<Friendship> friendships = friendshipRepository.findByUser1OrUser2(sender, receiver);
        for (Friendship friendship : friendships) {
            if (friendship.getUser1().equals(sender) && friendship.getUser2().equals(receiver) ||
                    friendship.getUser1().equals(receiver) && friendship.getUser2().equals(sender)) {
                return friendship;
            }
        }
        return null;
    }
}

