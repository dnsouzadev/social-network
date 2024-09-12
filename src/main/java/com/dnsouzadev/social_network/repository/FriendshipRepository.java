package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.Friendship;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByUser1OrUser2(User user1, User user2);
}
