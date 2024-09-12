package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserDetailsDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.exception.CadastroException;
import com.dnsouzadev.social_network.exception.LoginException;
import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void signup(UserDetailsDto userDto) {
        try {
            boolean userExists = userRepository.existsUserByUsername(userDto.username());
            if (userExists) throw new CadastroException("User already exists");

            User user = new User(userDto.username(), userDto.password());
            userRepository.save(user);
        } catch (Exception e) {
            throw new CadastroException(e.getMessage());
        }
    }

    @Transactional
    public void login(UserDetailsDto userDto) {
        try {
            Optional<User> user = userRepository.findByUsername(userDto.username());

            if (user.isEmpty()) throw new RuntimeException("User not found");

            if (!user.get().getPassword().equals(userDto.password())) throw new LoginException("Invalid password");

        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().
                    map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getTypeAccount())).
                    collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void change(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            user.changeTypeAccount(user.getTypeAccount() == TYPE_ACOOUNT.PUBLIC ? TYPE_ACOOUNT.HIDDEN : TYPE_ACOOUNT.PUBLIC);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllPublicUsers() {
        try {
            List<User> users = userRepository.findAllByTypeAccount(TYPE_ACOOUNT.PUBLIC);
            return users.stream().
                    map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getTypeAccount())).
                    collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


}
