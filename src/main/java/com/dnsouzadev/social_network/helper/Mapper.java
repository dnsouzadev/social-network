package com.dnsouzadev.social_network.helper;

import com.dnsouzadev.social_network.domain.model.*;
import com.dnsouzadev.social_network.dto.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getTypeAccount());
    }

    public FriendRequestResponseDto toFriendRequestResponseDto(FriendRequest fr) {
        return new FriendRequestResponseDto(fr.getId(),
                fr.getSender().getUsername(),
                fr.getReceiver().getUsername(),
                fr.getStatus().name());
    }

    public User toUserAdmin(UserDetailsAdminDto user) {
        return new User(user.firstName(), user.lastName(), user.username(), user.password(), user.role());
    }

    public PostDto toPostDto(Post post) {
        return new PostDto(post.getId(), post.getUser().getUsername(), post.getContent(), toSetLikeDto(post.getLikes()), toSetCommentDto(post.getComments()), post.getCreatedAt());
    }

    public Set<LikeDto> toSetLikeDto(Set<Like> likes) {
        return likes.stream().map(like -> new LikeDto(like.getUser().getUsername())).collect(Collectors.toSet());
    }

    public Set<CommentDto> toSetCommentDto(Set<Comment> comments) {
        return comments.stream().map(c -> new CommentDto(c.getId(), c.getUser().getFirstName(), c.getUser().getLastName(), c.getUser().getUsername(), c.getContent(), c.getCreatedAt())).collect(Collectors.toSet());
    }
}
