package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.domain.enums.TypeAccount;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        TypeAccount typeAccount,
        String fullName // Novo campo para nome completo
) {
    public UserResponseDto(Long id, String firstName, String lastName, String username, TypeAccount typeAccount) {
        this(id, firstName, lastName, username, typeAccount, firstName + " " + lastName); // Concatena o nome completo
    }
}
