package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.Friendship;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByUser1OrUser2(User user1, User user2);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Friendship f WHERE (f.user1.username = :username AND f.user2.username = :friend) OR (f.user1.username = :friend AND f.user2.username = :username)")
    boolean existsByUsernameAndFriend(String username, String friend);
}
