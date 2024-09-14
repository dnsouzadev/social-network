package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.FriendRequest;
import com.dnsouzadev.social_network.model.FriendRequestStatus;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, FriendRequestStatus status);

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver = ?1 AND fr.status = 'PENDING'")
    List<FriendRequest> getFriendRequests(User user);

}
