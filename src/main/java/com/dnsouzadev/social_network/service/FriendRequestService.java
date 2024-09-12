package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.FriendRequestResponseDto;
import com.dnsouzadev.social_network.model.FriendRequest;
import com.dnsouzadev.social_network.model.FriendRequestStatus;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.FriendRequestRepository;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        Optional<FriendRequest> alreadyFriends = friendRequestRepository
                .findBySenderAndReceiverAndStatus(sender, receiver, FriendRequestStatus.ACCEPTED);

        if (existingRequest.isPresent()) throw new RuntimeException("Friend request already sent");
        if (alreadyFriends.isPresent()) throw new RuntimeException("Users are already friends");


        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);

        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestResponseDto> getFriendRequests(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        var fr = friendRequestRepository.getFriendRequests(user);

        return fr.stream()
                .map(friendRequest -> new FriendRequestResponseDto(
                        friendRequest.getId(),
                        friendRequest.getSender().getUsername(),
                        friendRequest.getReceiver().getUsername(),
                        friendRequest.getStatus().name()
                ))
                .toList();
    }

    public void acceptFriendRequest(Long id) {
        FriendRequest friendRequest = friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        friendRequest.accept();
        friendRequestRepository.save(friendRequest);
    }

    public void rejectFriendRequest(Long id) {
        FriendRequest friendRequest = friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        deleteFriendRequest(id);
    }

    public void deleteFriendRequest(Long id) {
        FriendRequest friendRequest = friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        friendRequestRepository.delete(friendRequest);
    }
}