package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserDetailsDto;
import com.dnsouzadev.social_network.dto.UserLoginDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.exception.CadastroException;
import com.dnsouzadev.social_network.exception.LoginException;
import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.UserRepository;
import com.dnsouzadev.social_network.security.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }


    @Transactional
    public void signup(UserDetailsDto userDto) {
        try {
            boolean userExists = userRepository.existsUserByUsername(userDto.username());
            if (userExists) throw new CadastroException("User already exists");

            User user = new User(userDto.firstName(), userDto.lastName(), userDto.username(), userDto.password());
            userRepository.save(user);
        } catch (Exception e) {
            throw new CadastroException(e.getMessage());
        }
    }

    public String login(UserLoginDto dto) {
        if (dto.password() == null) throw new LoginException("Senha invalida");

        Optional<User> user = userRepository.findByUsername(dto.username());
        if (user.isEmpty()) throw new LoginException("Usuario nao existe");

        if (!user.get().getPassword().equals(dto.password())) throw new LoginException("Senha invalida");

        User usuario = user.get();
        return tokenService.generateToken(usuario);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(user -> new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getTypeAccount())).collect(Collectors.toList());
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
            return users.stream().map(user -> new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getTypeAccount())).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDto getProfile(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow();
            return new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getTypeAccount());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
