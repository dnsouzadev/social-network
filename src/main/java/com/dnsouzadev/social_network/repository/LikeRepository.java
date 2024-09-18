package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.Like;
import com.dnsouzadev.social_network.model.Post;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndPost(User user, Post post);

    void deleteLikeByPostId(Long postId);
}
