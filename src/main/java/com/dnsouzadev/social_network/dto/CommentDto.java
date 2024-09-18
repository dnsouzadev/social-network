package com.dnsouzadev.social_network.dto;

public record CommentDto(
        Long id,
        String username,
        String commentContent,
        String createdAt
) {
}
