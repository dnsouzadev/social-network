package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        TYPE_ACOOUNT typeAccount,
        String fullName // Novo campo para nome completo
) {
    public UserResponseDto(Long id, String firstName, String lastName, String username, TYPE_ACOOUNT typeAccount) {
        this(id, firstName, lastName, username, typeAccount, firstName + " " + lastName); // Concatena o nome completo
    }
}
