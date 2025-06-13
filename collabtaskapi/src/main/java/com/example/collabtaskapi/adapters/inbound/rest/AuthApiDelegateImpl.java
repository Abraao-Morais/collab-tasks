package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.controllers.AuthApiDelegate;
import com.example.collabtaskapi.dtos.AuthResponse;
import com.example.collabtaskapi.dtos.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthApiDelegateImpl implements AuthApiDelegate {

    private final RestAuthUseCase restAuthUseCase;

    public AuthApiDelegateImpl(RestAuthUseCase restAuthUseCase) {
        this.restAuthUseCase = restAuthUseCase;
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(restAuthUseCase.login(authRequest));
        return ResponseEntity.ok(authResponse);
    }
}
