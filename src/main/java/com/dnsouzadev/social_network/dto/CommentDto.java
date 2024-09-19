package com.dnsouzadev.social_network.dto;

import java.util.Date;

public record CommentDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        String commentContent,
        Date createdAt
) {
}
