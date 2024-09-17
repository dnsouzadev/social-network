package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
