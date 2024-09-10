package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
