package com.example.collabtaskapi.application.services;

import com.example.collabtaskapi.infrastructure.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    private final JwtService jwtService;

    public AuthServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication authentication){
        return jwtService.generateToken(authentication);
    }
}
