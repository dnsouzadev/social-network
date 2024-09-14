package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.FriendRequestResponseDto;
import com.dnsouzadev.social_network.model.FriendRequest;
import com.dnsouzadev.social_network.model.FriendRequestStatus;
import com.dnsouzadev.social_network.model.Friendship;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.FriendRequestRepository;
import com.dnsouzadev.social_network.repository.FriendshipRepository;
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
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendFriendRequest(String senderUsername, String receiverUsername) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByUsername(receiverUsername).orElseThrow(() -> new RuntimeException("Receiver not found"));
        User receiverTypeAccount = userRepository.findByUsername(receiverUsername).orElseThrow(() -> new RuntimeException("Receiver not found"));
        String typeAccountReceiver = String.valueOf(receiverTypeAccount.getTypeAccount());

        Optional<FriendRequest> existingRequest = friendRequestRepository
                .findBySenderAndReceiverAndStatus(sender, receiver, FriendRequestStatus.PENDING);

        Optional<FriendRequest> alreadyFriends = friendRequestRepository
                .findBySenderAndReceiverAndStatus(sender, receiver, FriendRequestStatus.ACCEPTED);

        if (existingRequest.isPresent()) throw new RuntimeException("Friend request already sent");
        if (alreadyFriends.isPresent()) throw new RuntimeException("Users are already friends");
        if (sender.equals(receiver)) throw new RuntimeException("You can't send a friend request to yourself");
        if (typeAccountReceiver.equals("HIDDEN")) throw new RuntimeException("You can't send a friend request to this user");

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

    public void acceptFriendRequest(Long id, String username) {
        FriendRequest friendRequest = friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        boolean isReceiver = friendRequest.getReceiver().getUsername().equals(username);

        if (!isReceiver) throw new RuntimeException("You can't accept a friend request that you didn't receive");

        friendRequest.accept();
        friendRequestRepository.save(friendRequest);

        Friendship friendship = new Friendship();
        friendship.setUser1(friendRequest.getSender());
        friendship.setUser2(friendRequest.getReceiver());
        friendshipRepository.save(friendship);;
    }

    public void rejectFriendRequest(Long id) {
        FriendRequest friendRequest = friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        deleteFriendRequest(id);
    }

    private void deleteFriendRequest(Long id) {
        FriendRequest friendRequest = friendRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        friendRequestRepository.delete(friendRequest);
    }
}