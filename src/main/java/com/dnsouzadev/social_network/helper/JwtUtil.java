package com.dnsouzadev.social_network.helper;

import com.dnsouzadev.social_network.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Autowired
    private TokenService tokenService;

    public String getUsername(HttpServletRequest request) {
        String jwt = tokenService.getJwt(request);

        return tokenService.getSubject(jwt);
    }
}
