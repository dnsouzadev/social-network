package com.dnsouzadev.social_network.dto;

import java.util.Date;
import java.util.Set;

public record PostDto(
        Long id,
        String username,
        String postContent,
        Set<LikeDto> like,
        Set<CommentDto> comment,
        Date createdAt
        ) {
}
