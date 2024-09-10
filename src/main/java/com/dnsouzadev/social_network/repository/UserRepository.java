package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByTypeAccount(TYPE_ACOOUNT typeAcoount);
}
