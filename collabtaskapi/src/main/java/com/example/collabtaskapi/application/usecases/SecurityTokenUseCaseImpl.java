package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;

public class SecurityTokenUseCaseImpl implements SecurityTokenUseCase {

    private final RepositoryTokenPort repositoryTokenPort;

    public SecurityTokenUseCaseImpl(RepositoryTokenPort repositoryTokenPort) {
        this.repositoryTokenPort = repositoryTokenPort;
    }

    @Override
    public boolean tokenIsValid(String jwtToken) {
        Token token = repositoryTokenPort.findByToken(jwtToken)
                .orElseThrow(() -> new EntityNotFoundException("Token not found with token " + jwtToken));
        return token.isRevoked();
    }
}
