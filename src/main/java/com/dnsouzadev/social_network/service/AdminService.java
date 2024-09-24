package com.dnsouzadev.social_network.service;

import com.dnsouzadev.social_network.domain.model.User;
import com.dnsouzadev.social_network.dto.UserDetailsAdminDto;
import com.dnsouzadev.social_network.exception.CadastroException;
import com.dnsouzadev.social_network.helper.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserService userService;

    public void signup(UserDetailsAdminDto userDto) {
        try {
            User userExists = userService.findByUsername(userDto.username());
            if (userExists != null) throw new CadastroException("User already exists");

            User user = mapper.toUserAdmin(userDto);
            userService.saveUser(user);

        } catch (Exception e) {
            throw new CadastroException(e.getMessage());
        }
    }

}
