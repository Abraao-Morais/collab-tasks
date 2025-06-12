package com.example.collabtaskapi.adapters.inbound.restservices;

import com.example.collabtaskapi.application.services.AccountServiceImpl;
import com.example.collabtaskapi.controllers.AccountApiDelegate;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class AccountApiDelegateImpl implements AccountApiDelegate {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    public AccountApiDelegateImpl(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @Override
    public ResponseEntity<AccountResponse> createNewAccount(AccountRequest accountRequest) {
        AccountResponse createdAccount = accountServiceImpl.createNewAccount(accountRequest);
        return ResponseEntity.created(URI.create("/account/" + createdAccount.getId())).body(createdAccount);
    }

    @Override
    public ResponseEntity<Void> deleteAccountByID(Integer id) {
        accountServiceImpl.deleteAccountByID(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AccountResponse> getAccountById(Integer id) {
        AccountResponse accountResponse = accountServiceImpl.getAccountById(id);
        return ResponseEntity.ok(accountResponse);
    }
    @Override
    public ResponseEntity<List<AccountResponse>> listAllAccounts() {
        List<AccountResponse> accountResponses = accountServiceImpl.listAllAccounts();
        if (accountResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accountResponses);
    }

    @Override
    public ResponseEntity<AccountResponse> updateAccountByID(Integer id, AccountRequest accountRequest) {
        AccountResponse accountResponse = accountServiceImpl.updateAccountByID(id, accountRequest);
        return ResponseEntity.ok(accountResponse);
    }
}
