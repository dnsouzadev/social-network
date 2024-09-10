package com.dnsouzadev.social_network.repository;

import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByTypeAccount(TYPE_ACOOUNT typeAcoount);

    Boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);
}
