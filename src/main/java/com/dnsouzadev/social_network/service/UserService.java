package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserCadastroDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void signup(UserCadastroDto userDto) {
        User user = new User(userDto.username(), userDto.password(), userDto.typeAccount());
        userRepository.save(user);
    }

    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().
                map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getTypeAccount())).
                collect(Collectors.toList());
    }
}
