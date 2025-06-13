package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestAccountUseCase;
import com.example.collabtaskapi.controllers.AccountApiDelegate;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class AccountApiDelegateImpl implements AccountApiDelegate {

    private final RestAccountUseCase restAccountUseCase;

    public AccountApiDelegateImpl(RestAccountUseCase restAccountUseCase) {
        this.restAccountUseCase = restAccountUseCase;
    }

    @Override
    public ResponseEntity<AccountResponse> createNewAccount(AccountRequest accountRequest) {
        AccountResponse createdAccount = restAccountUseCase.createNewAccount(accountRequest);
        return ResponseEntity.created(URI.create("/account/" + createdAccount.getId())).body(createdAccount);
    }

    @Override
    public ResponseEntity<Void> deleteAccountByID(Integer id) {
        restAccountUseCase.deleteAccountByID(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AccountResponse> getAccountById(Integer id) {
        AccountResponse accountResponse = restAccountUseCase.getAccountById(id);
        return ResponseEntity.ok(accountResponse);
    }
    @Override
    public ResponseEntity<List<AccountResponse>> listAllAccounts() {
        List<AccountResponse> accountResponses = restAccountUseCase.listAllAccounts();
        if (accountResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountResponses);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccountByID(Integer id, AccountRequest accountRequest) {
        AccountResponse accountResponse = restAccountUseCase.updateAccountByID(id, accountRequest);
        return ResponseEntity.ok(accountResponse);
    }
}
