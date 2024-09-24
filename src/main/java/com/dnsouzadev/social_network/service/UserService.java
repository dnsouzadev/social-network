package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserDetailsDto;
import com.dnsouzadev.social_network.dto.UserLoginDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.exception.CadastroException;
import com.dnsouzadev.social_network.exception.LoginException;
import com.dnsouzadev.social_network.domain.enums.TypeAccount;
import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.helper.Mapper;
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

    @Autowired
    private Mapper mapper;

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Transactional(readOnly = true)
    public boolean existsByUsernameAndIsHidden(String username) {
        return userRepository.existsByUsernameAndIsHidden(username);
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void signup(UserDetailsDto userDto) {
        try {
            boolean userExists = userRepository.existsUserByUsername(userDto.username());
            if (userExists) throw new CadastroException("User already exists");

            User user = createUser(userDto);
            saveUser(user);
        } catch (Exception e) {
            throw new CadastroException(e.getMessage());
        }
    }

    public String login(UserLoginDto dto) {
        if (dto.password() == null) throw new LoginException("Senha invalida");

        User user = findByUsername(dto.username());

        if (!user.getPassword().equals(dto.password())) throw new LoginException("Senha invalida");

        return tokenService.generateToken(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(user -> mapper.toUserResponseDto(user)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void change(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            user.changeTypeAccount(user.getTypeAccount() == TypeAccount.PUBLIC ? TypeAccount.HIDDEN : TypeAccount.PUBLIC);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllPublicUsers() {
        try {
            List<User> users = userRepository.findAllByTypeAccount(TypeAccount.PUBLIC);
            return users.stream().map(user -> mapper.toUserResponseDto(user)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public UserResponseDto getProfile(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow();
            return mapper.toUserResponseDto(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private User createUser(UserDetailsDto userDto) {
        return new User(userDto.firstName(), userDto.lastName(), userDto.username(), userDto.password());
    }

}
