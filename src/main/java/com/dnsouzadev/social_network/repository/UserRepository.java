package com.dnsouzadev.social_network.repository;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByTypeAccount(TYPE_ACOOUNT typeAcoount);

    Boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = :username AND u.typeAccount = 'PRIVATE'")
    boolean existsByUsernameAndIsHidden(String username);

    @Query("SELECT u.username FROM User u WHERE u.id = :postId")
    Optional<User> findByPostId(Long postId);

}
