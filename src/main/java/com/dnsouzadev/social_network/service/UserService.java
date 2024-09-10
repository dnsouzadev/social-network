package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.dto.UserCadastroDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.model.TYPE_ACOOUNT;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Void> signup(UserCadastroDto userDto) {
        try {
            User user = new User(userDto.username(), userDto.password(), userDto.typeAccount());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<UserResponseDto>> findAll() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users.stream().
                    map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getTypeAccount())).
                    collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Void> change(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            user.changeTypeAccount(user.getTypeAccount() == TYPE_ACOOUNT.PUBLIC ? TYPE_ACOOUNT.HIDDEN : TYPE_ACOOUNT.PUBLIC);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<UserResponseDto>> findAllPublicUsers() {
        try {
            List<User> users = userRepository.findAllByTypeAccount(TYPE_ACOOUNT.PUBLIC);
            return ResponseEntity.ok(users.stream().
                    map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getTypeAccount())).
                    collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
