package com.dnsouzadev.social_network.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "account")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private TYPE_ACOOUNT typeAccount;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.typeAccount = TYPE_ACOOUNT.valueOf("PUBLIC");
    }

    public void changeTypeAccount(TYPE_ACOOUNT typeAccount) {
        this.typeAccount = typeAccount;
    }
}
