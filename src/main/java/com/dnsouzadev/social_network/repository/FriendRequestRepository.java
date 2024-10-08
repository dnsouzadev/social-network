package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.domain.model.FriendRequest;
import com.dnsouzadev.social_network.domain.enums.FriendRequestStatus;
import com.dnsouzadev.social_network.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver = ?1 AND fr.status = 'PENDING'")
    List<FriendRequest> getFriendRequests(User user);

}
