package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.dto.AccountRequest;
import com.example.collabtaskapi.dto.AccountResponse;

import java.util.List;

public interface AccountUseCase {

    public List<AccountResponse> listAllAccounts();
    public AccountResponse createNewAccount(AccountRequest accountRequest);
    public AccountResponse getAccountById(Integer id);
    public void deleteAccountByID(Integer id);
    public AccountResponse updateAccountByID(Integer id, AccountRequest accountRequest);
}
