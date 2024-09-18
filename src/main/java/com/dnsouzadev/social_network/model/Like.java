package com.dnsouzadev.social_network.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "`like`")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Usuário que deu o like

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // Post que recebeu o like

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
        this.createdAt = new Date();
    }

}