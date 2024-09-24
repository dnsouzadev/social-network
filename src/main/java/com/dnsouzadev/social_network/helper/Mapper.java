package com.dnsouzadev.social_network.helper;

import com.dnsouzadev.social_network.domain.model.FriendRequest;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.dto.FriendRequestResponseDto;
import com.dnsouzadev.social_network.dto.UserDetailsAdminDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import org.springframework.stereotype.Component;

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
}
