package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.AccountUseCase;
import com.example.collabtaskapi.controllers.AccountApiDelegate;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private final AccountUseCase accountUseCase;

    public AccountApiDelegateImpl(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @Override
    public ResponseEntity<AccountResponse> createNewAccount(AccountRequest accountRequest) {
        AccountResponse createdAccount = accountUseCase.createNewAccount(accountRequest);
        return ResponseEntity.created(URI.create("/account/" + createdAccount.getId())).body(createdAccount);
    }

    @Override
    public ResponseEntity<Void> deleteAccountByID(Integer id) {
        accountUseCase.deleteAccountByID(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AccountResponse> getAccountById(Integer id) {
        AccountResponse accountResponse = accountUseCase.getAccountById(id);
        return ResponseEntity.ok(accountResponse);
    }
    @Override
    public ResponseEntity<List<AccountResponse>> listAllAccounts() {
        List<AccountResponse> accountResponses = accountUseCase.listAllAccounts();
        if (accountResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountResponses);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccountByID(Integer id, AccountRequest accountRequest) {
        AccountResponse accountResponse = accountUseCase.updateAccountByID(id, accountRequest);
        return ResponseEntity.ok(accountResponse);
    }
}
