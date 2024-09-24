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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private Mapper mapper;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public boolean existsByUsernameAndIsHidden(String username) {
        return userRepository.existsByUsernameAndIsHidden(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void signup(UserDetailsDto userDto) {
        try {
            User userExist = findByUsername(userDto.username());
            if (userExist != null) throw new CadastroException("Usuario ja cadastrado");

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

    public List<UserResponseDto> findAll() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(user -> mapper.toUserResponseDto(user)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void change(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            user.changeTypeAccount(user.getTypeAccount() == TypeAccount.PUBLIC ? TypeAccount.HIDDEN : TypeAccount.PUBLIC);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<UserResponseDto> findAllPublicUsers() {
        try {
            List<User> users = userRepository.findAllByTypeAccount(TypeAccount.PUBLIC);
            return users.stream().map(user -> mapper.toUserResponseDto(user)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public User createUser(UserDetailsDto userDto) {
        return new User(userDto.firstName(), userDto.lastName(), userDto.username(), userDto.password());
    }

}
