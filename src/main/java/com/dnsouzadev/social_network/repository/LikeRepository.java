package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.domain.model.Like;
import com.dnsouzadev.social_network.domain.model.Post;
import com.dnsouzadev.social_network.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndPost(User user, Post post);

    void deleteLikeByPostId(Long postId);
}
