package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;

public class SecurityAccountUseCaseImpl implements SecurityAccountUseCase {

    private final RepositoryAccountPort repositoryAccountPort;

    public SecurityAccountUseCaseImpl(RepositoryAccountPort repositoryAccountPort) {
        this.repositoryAccountPort = repositoryAccountPort;
    }

    @Override
    public Account getAccountByName(String name) {
        return repositoryAccountPort.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with name " + name));
    }
}
