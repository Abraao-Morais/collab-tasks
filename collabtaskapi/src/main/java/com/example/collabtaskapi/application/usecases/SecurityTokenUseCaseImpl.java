package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.domain.Token;

public class SecurityTokenUseCaseImpl implements SecurityTokenUseCase {

    private final RepositoryTokenPort repositoryTokenPort;

    public SecurityTokenUseCaseImpl(RepositoryTokenPort repositoryTokenPort) {
        this.repositoryTokenPort = repositoryTokenPort;
    }

    @Override
    public Token findByToken(String token) {
        return repositoryTokenPort.findByToken(token);
    }
}
