package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.controllers.AuthApiDelegate;
import com.example.collabtaskapi.dtos.AuthResponse;
import com.example.collabtaskapi.dtos.AuthRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static java.util.Objects.nonNull;

@Component
public class AuthApiDelegateImpl implements AuthApiDelegate {

    private static final Logger log = LoggerFactory.getLogger(AuthApiDelegateImpl.class);

    private final RestAuthUseCase restAuthUseCase;

    public AuthApiDelegateImpl(RestAuthUseCase restAuthUseCase) {
        this.restAuthUseCase = restAuthUseCase;
    }

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        log.info("Requisição de login recebida para o usuário: {}", authRequest.getUsername());
        String token = restAuthUseCase.login(authRequest);
        log.info("Token JWT gerado com sucesso para usuário: {}", authRequest.getUsername());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @Override
    public ResponseEntity<Void> logout() {
        String token = getCurrentRequestToken();
        if (token != null) {
            log.info("Requisição de logout recebida com token: {}", token);
        } else {
            log.warn("Requisição de logout recebida sem token válido");
        }

        restAuthUseCase.logout(token);
        log.info("Logout realizado com sucesso");
        return ResponseEntity.ok().build();
    }

    private String getCurrentRequestToken() {
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String header = request.getHeader("Authorization");
        if (nonNull(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

