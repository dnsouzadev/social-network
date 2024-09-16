package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.model.ROLE;

public record UserDetailsAdminDto(String firstName, String lastName, String username, String password, ROLE role) {
}
