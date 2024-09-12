package com.dnsouzadev.social_network.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    public FriendRequest() {
        this.createdAt = new Date();
        this.status = FriendRequestStatus.PENDING;
    }
}
