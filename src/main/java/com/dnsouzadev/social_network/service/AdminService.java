package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserDetailsAdminDto;
import com.dnsouzadev.social_network.exception.CadastroException;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public void signup(UserDetailsAdminDto userDto) {
        try {
            boolean userExists = userRepository.existsUserByUsername(userDto.username());
            if (userExists) throw new CadastroException("User already exists");

            User user = new User(userDto.username(), userDto.password(), userDto.role());
            userRepository.save(user);

        } catch (Exception e) {
            throw new CadastroException(e.getMessage());
        }
    }

}
