package com.dnsouzadev.social_network.helper;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseModel {
    public ResponseEntity<String> sendResponse(String message) {
        String jsonMessage = "{\"message\": \"" + message + "\"}";
        return ResponseEntity.ok(jsonMessage);
    }

    public ResponseEntity<String> sendJwtResponse(String jwt) {
        String jsonMessage = "{\"jwt\": \"" + jwt + "\"}";
        return ResponseEntity.ok(jsonMessage);
    }
}
