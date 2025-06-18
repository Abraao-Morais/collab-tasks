package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;

import java.util.List;

import static java.util.Objects.isNull;

public class SecurityTokenUseCaseImpl implements SecurityTokenUseCase {

    private final RepositoryTokenPort repositoryTokenPort;
    private static final List<String> IGNORED_PATHS = List.of("login", "swagger", "api-docs", "h2-console", "webjars");

    public SecurityTokenUseCaseImpl(RepositoryTokenPort repositoryTokenPort) {
        this.repositoryTokenPort = repositoryTokenPort;
    }

    @Override
    public boolean tokenIsValid(String jwtToken){
        Token token = repositoryTokenPort.findByToken(jwtToken)
                .orElseThrow(() -> new EntityNotFoundException("Token not found with token " + jwtToken));
        return token.isRevoked();
    }

    @Override
    public boolean pathIsValid(String path) {
        return IGNORED_PATHS.stream().anyMatch(path::contains);
    }

}
