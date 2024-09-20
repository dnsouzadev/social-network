package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.domain.enums.TypeAccount;
import com.dnsouzadev.social_network.domain.model.User;

public record ProfileResponseDto(
        String firstName,
        String lastName,
        String fullName, // Novo campo para nome completo
        String username,
        TypeAccount typeAccount,
        ListPostsDto posts // Novo campo para lista de posts
) {
    public ProfileResponseDto(User user, ListPostsDto posts) {
        this(user.getFirstName(), user.getLastName(), user.getFirstName() + " " + user.getLastName(), user.getUsername(), user.getTypeAccount(), posts);
    }
}
