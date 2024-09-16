package com.dnsouzadev.social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDetailsDto(
        @NotBlank @Size(min = 3, max = 50) @Pattern(regexp = "^\\p{Alpha}{2,50}$")
        String firstName,
        @NotBlank @Size(min = 3, max = 50) @Pattern(regexp = "^\\p{Alpha}{2,50}$")
        String lastName,
        @NotBlank @Size(min = 8, max = 10) @Pattern(regexp = "^\\p{Alnum}{8,10}$")
        String username,
        @NotBlank @Size(min = 8, max = 12) @Pattern(regexp = "^\\p{Alnum}{8,12}$")
        String password) {
}
