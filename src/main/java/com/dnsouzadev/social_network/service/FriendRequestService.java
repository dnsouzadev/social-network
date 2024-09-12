package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.model.FriendRequest;
import com.dnsouzadev.social_network.model.FriendRequestStatus;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.FriendRequestRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendFriendRequest(String senderUsername, String receiverUsername) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername).orElseThrow(() -> new RuntimeException("Receiver not found"));

        Optional<FriendRequest> existingRequest = friendRequestRepository
                .findBySenderAndReceiverAndStatus(sender, receiver, FriendRequestStatus.PENDING);

        if (existingRequest.isPresent()) {
            throw new RuntimeException("Friend request already sent");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);

        friendRequestRepository.save(friendRequest);
    }
}