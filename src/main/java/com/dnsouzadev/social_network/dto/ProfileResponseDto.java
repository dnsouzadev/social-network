package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.model.Post;
import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;

import java.util.List;

public record ProfileResponseDto(
        String firstName,
        String lastName,
        String fullName, // Novo campo para nome completo
        String username,
        TYPE_ACOOUNT typeAccount,
        ListPostsDto posts // Novo campo para lista de posts
) {
    public ProfileResponseDto(User user, ListPostsDto posts) {
        this(user.getFirstName(), user.getLastName(), user.getFirstName() + " " + user.getLastName(), user.getUsername(), user.getTypeAccount(), posts);
    }
}
