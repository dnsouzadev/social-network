package com.dnsouzadev.social_network.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "account")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private TYPE_ACOOUNT typeAccount;

    public User(String username, String password, String typeAccount) {
    }
}
