package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user.username = ?1")
    List<Post> findByUsername(String username);
}
