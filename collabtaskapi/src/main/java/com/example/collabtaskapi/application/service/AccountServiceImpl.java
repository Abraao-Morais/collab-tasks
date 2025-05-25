package com.example.collabtaskapi.application.service;

import com.example.collabtaskapi.application.usecases.AccountUseCase;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.AccountRepository;
import com.example.collabtaskapi.dto.AccountRequest;
import com.example.collabtaskapi.dto.AccountResponse;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mapper.AccountMapper;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountUseCase {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountResponse> listAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::accountToAccountResponse).toList();
    }

    @Override
    public AccountResponse createNewAccount(AccountRequest accountRequest) {
        Account account = accountMapper.accountRequestToAccount(accountRequest);
        account.setActive(true);
        account = accountRepository.save(account);
        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public void deleteAccountByID(Integer id) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));

        existingAccount.setActive(false);
        accountRepository.save(existingAccount);
    }

    @Override
    public AccountResponse updateAccountByID(Integer id, AccountRequest accountRequest) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));

        existingAccount.setName(accountRequest.getName());
        existingAccount.setEmail(accountRequest.getEmail());
        if (accountRequest.getProfilePhotoUrl() != null){
            existingAccount.setProfilePhotoUrl(accountRequest.getProfilePhotoUrl().toString());
        }

        Account savedAccount = accountRepository.save(existingAccount);
        return accountMapper.accountToAccountResponse(savedAccount);
    }





}
