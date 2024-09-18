package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.model.Comment;
import com.dnsouzadev.social_network.model.Like;

import java.util.Date;
import java.util.Set;

public record PostDto(
        Long id,
        String username,
        String postContent,
        Set<LikeDto> like,
        Set<Comment> comment,
        Date createdAt
        ) {
}
