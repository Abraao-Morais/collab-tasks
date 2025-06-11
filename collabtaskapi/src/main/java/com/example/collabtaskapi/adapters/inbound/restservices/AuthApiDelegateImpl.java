package com.example.collabtaskapi.adapters.inbound.restservices;

import com.example.collabtaskapi.application.services.AuthServiceImpl;
import com.example.collabtaskapi.controllers.AuthApiDelegate;
import com.example.collabtaskapi.dtos.AuthenticateUser200Response;
import com.example.collabtaskapi.dtos.AuthenticateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthApiDelegateImpl implements AuthApiDelegate {

    private final AuthServiceImpl authService;

    public AuthApiDelegateImpl(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<AuthenticateUser200Response> authenticateUser(AuthenticateUserRequest authenticateUserRequest) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(authenticateUserRequest.getUsername(), authenticateUserRequest.getPassword());
        AuthenticateUser200Response token = new AuthenticateUser200Response();
        token.setToken(authService.authenticate(authentication));
        return ResponseEntity.ok(token);
    }
}
