package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestAccountUseCase;
import com.example.collabtaskapi.controllers.AccountApiDelegate;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private static final Logger log = LoggerFactory.getLogger(AccountApiDelegateImpl.class);

    private final RestAccountUseCase restAccountUseCase;

    public AccountApiDelegateImpl(RestAccountUseCase restAccountUseCase) {
        this.restAccountUseCase = restAccountUseCase;
    }

    @Override
    public ResponseEntity<AccountResponse> createNewAccount(AccountRequest accountRequest) {
        log.info("Requisição para criar nova conta: {}", accountRequest);
        AccountResponse createdAccount = restAccountUseCase.createNewAccount(accountRequest);
        log.info("Conta criada com ID: {}", createdAccount.getId());
        return ResponseEntity.created(URI.create("/account/" + createdAccount.getId())).body(createdAccount);
    }

    @Override
    public ResponseEntity<Void> deleteAccountByID(Integer id) {
        log.info("Requisição para deletar conta com ID: {}", id);
        restAccountUseCase.deleteAccountByID(id);
        log.info("Conta com ID {} deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AccountResponse> getAccountById(Integer id) {
        log.info("Requisição para obter conta com ID: {}", id);
        AccountResponse accountResponse = restAccountUseCase.getAccountById(id);
        log.debug("Conta encontrada: {}", accountResponse);
        return ResponseEntity.ok(accountResponse);
    }

    @Override
    public ResponseEntity<List<AccountResponse>> listAllAccounts() {
        log.info("Requisição para listar todas as contas");
        List<AccountResponse> accountResponses = restAccountUseCase.listAllAccounts();
        log.info("Total de contas encontradas: {}", accountResponses.size());
        if (accountResponses.isEmpty()) {
            log.info("Nenhuma conta encontrada");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountResponses);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccountByID(Integer id, AccountRequest accountRequest) {
        log.info("Requisição para atualizar conta com ID: {}", id);
        AccountResponse accountResponse = restAccountUseCase.updateAccountByID(id, accountRequest);
        log.info("Conta com ID {} atualizada com sucesso", id);
        return ResponseEntity.ok(accountResponse);
    }
}

