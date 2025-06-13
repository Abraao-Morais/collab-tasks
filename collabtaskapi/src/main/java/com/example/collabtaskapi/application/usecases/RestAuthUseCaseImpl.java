package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.outbound.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

public class RestAuthUseCaseImpl implements RestAuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public RestAuthUseCaseImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String authenticate(Authentication authentication){
        Authentication authenticated = authenticationManager.authenticate(authentication);
        return tokenService.generateToken(authenticated);
    }
}
