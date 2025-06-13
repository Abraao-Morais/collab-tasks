package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.application.ports.outbound.AccountRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;

public class SecurityAccountUseCaseImpl implements SecurityAccountUseCase {

    private final AccountRepository accountRepository;

    public SecurityAccountUseCaseImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccountByName(String name) {
        return accountRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with name " + name));
    }
}
