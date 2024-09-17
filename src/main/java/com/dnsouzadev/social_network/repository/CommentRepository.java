package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
