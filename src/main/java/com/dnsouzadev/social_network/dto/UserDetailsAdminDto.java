package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.domain.enums.Role;

public record UserDetailsAdminDto(String firstName, String lastName, String username, String password, Role role) {
}
