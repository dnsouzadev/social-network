package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user.username = ?1")
    List<Post> findByUsername(String username);

    List<Post> findByUser(User user);
    
    @Query("DELETE FROM Post p WHERE p.user = ?1 AND p.id = ?2")
    void deleteByUserAndId(User user, Long id);

    Optional<Post> findPostById(Long id);
}
