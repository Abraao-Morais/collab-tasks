package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.controllers.AuthApiDelegate;
import com.example.collabtaskapi.dtos.AuthResponse;
import com.example.collabtaskapi.dtos.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthApiDelegateImpl implements AuthApiDelegate {

    private final RestAuthUseCase restAuthUseCase;

    public AuthApiDelegateImpl(RestAuthUseCase restAuthUseCase) {
        this.restAuthUseCase = restAuthUseCase;
    }

    @Override
    public ResponseEntity<AuthResponse> authenticateUser(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(restAuthUseCase.authenticate(authentication));
        return ResponseEntity.ok(authResponse);
    }
}
