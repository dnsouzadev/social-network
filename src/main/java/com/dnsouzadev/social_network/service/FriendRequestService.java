package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.FriendRequestResponseDto;
import com.dnsouzadev.social_network.domain.model.FriendRequest;
import com.dnsouzadev.social_network.domain.enums.FriendRequestStatus;
import com.dnsouzadev.social_network.domain.model.Friendship;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.helper.Mapper;
import com.dnsouzadev.social_network.repository.FriendRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;


    public void sendFriendRequest(String senderUsername, String receiverUsername) {
        User sender = userService.findByUsername(senderUsername);
        User receiver = userService.findByUsername(receiverUsername);
        String typeAccountReceiver = String.valueOf(receiver.getTypeAccount());

        Friendship friendship = friendshipService.checkFriendship(sender, receiver);

        if (friendship != null) throw new RuntimeException("You are already friends");

        if (sender.equals(receiver)) throw new RuntimeException("You can't send a friend request to yourself");

        if (typeAccountReceiver.equals("HIDDEN")) throw new RuntimeException("You can't send a friend request to this user");

        Optional<FriendRequest> existingRequest = friendRequestRepository
                .findBySenderAndReceiver(sender, receiver);

        if (existingRequest.isPresent()) {
            FriendRequestStatus status = existingRequest.get().getStatus();
            switch (status) {
                case ACCEPTED:
                    throw new RuntimeException("Friend request already accepted");
                case REJECTED:
                    throw new RuntimeException("Friend request already rejected");
                case PENDING:
                    throw new RuntimeException("Friend request already sent");
            }
        }
        createFriendRequest(sender, receiver);
    }

    public List<FriendRequestResponseDto> getFriendRequests(String username) {
        User user = userService.findByUsername(username);

        List<FriendRequest> fr = friendRequestRepository.getFriendRequests(user);

        return fr.stream()
                .map(friendRequest -> mapper.toFriendRequestResponseDto(friendRequest))
                .toList();
    }

    public void acceptFriendRequest(Long id, String username) {
        FriendRequest friendRequest = getFriendRequest(id);

        boolean isReceiver = friendRequest.getReceiver().getUsername().equals(username);

        if (!isReceiver) throw new RuntimeException("You can't accept a friend request that you didn't receive");

        friendRequest.accept();
        friendRequestRepository.save(friendRequest);

        friendshipService.createFriendship(friendRequest.getSender(), friendRequest.getReceiver());
    }

    public void rejectFriendRequest(Long id, String username) {
        FriendRequest friendRequest = getFriendRequest(id);

        boolean isReceiver = friendRequest.getReceiver().getUsername().equals(username);

        if (!isReceiver) throw new RuntimeException("You can't accept a friend request that you didn't receive");

        deleteFriendRequest(id);
    }

    private void deleteFriendRequest(Long id) {
        FriendRequest friendRequest = getFriendRequest(id);

        friendRequestRepository.delete(friendRequest);
    }

    private FriendRequest getFriendRequest(Long id) {
        return friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));
    }

    public void deleteFriendRequestBySenderAndReceiver(User sender, User receiver) {
        var friendrequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver);

        if (friendrequest.isEmpty()) throw new RuntimeException("Friend request not found");

        friendRequestRepository.delete(friendrequest.get());

    }

    private void createFriendRequest(User sender, User receiver) {
        friendRequestRepository.save(new FriendRequest(sender, receiver));
    }
}