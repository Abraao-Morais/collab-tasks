package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.AuthUseCase;
import com.example.collabtaskapi.application.ports.outbound.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

public class AuthUseCaseImpl implements AuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthUseCaseImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    public String authenticate(Authentication authentication){
        Authentication authenticated = authenticationManager.authenticate(authentication);
        return tokenService.generateToken(authenticated);
    }
}
