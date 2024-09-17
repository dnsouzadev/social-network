package com.dnsouzadev.social_network.dto;

import com.dnsouzadev.social_network.model.Post;

import java.util.List;

public record ListPostsDto(List<PostDto> posts) {
}
