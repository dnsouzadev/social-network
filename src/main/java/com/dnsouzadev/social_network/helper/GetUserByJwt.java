package com.dnsouzadev.social_network.helper;

import com.dnsouzadev.social_network.model.User;
import com.dnsouzadev.social_network.security.TokenService;
import com.dnsouzadev.social_network.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserByJwt {

    @Autowired
    private TokenService tokenService;

    public String getUser(HttpServletRequest request) {
        String jwt = tokenService.getJwt(request);

        return tokenService.getSubject(jwt);
    }
}
