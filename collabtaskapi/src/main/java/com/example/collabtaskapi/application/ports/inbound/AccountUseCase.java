package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;

import java.util.List;

public interface AccountUseCase {

    List<AccountResponse> listAllAccounts();
    AccountResponse createNewAccount(AccountRequest accountRequest);
    AccountResponse getAccountById(Integer id);
    void deleteAccountByID(Integer id);
    AccountResponse updateAccountByID(Integer id, AccountRequest accountRequest);

}
