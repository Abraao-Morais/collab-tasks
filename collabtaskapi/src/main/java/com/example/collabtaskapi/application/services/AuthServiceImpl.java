package com.example.collabtaskapi.application.services;

import com.example.collabtaskapi.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public String authenticate(Authentication authentication){
        Authentication authenticated = authenticationManager.authenticate(authentication);
        return jwtService.generateToken(authenticated);
    }
}
