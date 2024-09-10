package com.dnsouzadev.social_network.controller;

import com.dnsouzadev.social_network.dto.UserCadastroDto;
import com.dnsouzadev.social_network.dto.UserResponseDto;
import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.repository.UserRepository;
import com.dnsouzadev.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserCadastroDto user) {
        return service.signup(user);
    }

    @GetMapping("/findPublic")
    public ResponseEntity<List<UserResponseDto>> findAllPublicUsers() {
        return service.findAllPublicUsers();
    }

}
