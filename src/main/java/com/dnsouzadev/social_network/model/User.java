package com.dnsouzadev.social_network.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import java.util.Date;

@Table(name = "account")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private TYPE_ACOOUNT typeAccount;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.typeAccount = TYPE_ACOOUNT.valueOf("PUBLIC");
        this.createdAt = new Date();
    }

    public void changeTypeAccount(TYPE_ACOOUNT typeAccount) {
        this.typeAccount = typeAccount;
    }
}
