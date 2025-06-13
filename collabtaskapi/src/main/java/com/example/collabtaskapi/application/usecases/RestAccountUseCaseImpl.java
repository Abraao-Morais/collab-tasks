package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.SecurityEncoderPort;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.application.ports.inbound.RestAccountUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;

import java.util.List;

public class RestAccountUseCaseImpl implements RestAccountUseCase {

    private final RepositoryAccountPort repositoryAccountPort;
    private final SecurityEncoderPort securityEncoderPort;
    private final AccountMapper accountMapper;

    public RestAccountUseCaseImpl(RepositoryAccountPort repositoryAccountPort, SecurityEncoderPort securityEncoderPort, AccountMapper accountMapper) {
        this.repositoryAccountPort = repositoryAccountPort;
        this.securityEncoderPort = securityEncoderPort;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountResponse> listAllAccounts() {
        return repositoryAccountPort.findAll().stream()
                .map(accountMapper::accountToAccountResponse).toList();
    }

    @Override
    public AccountResponse createNewAccount(AccountRequest accountRequest) {
        Account account = accountMapper.accountRequestToAccount(accountRequest);
        account.setActive(true);
        account.setPassword(securityEncoderPort.encode(accountRequest.getPassword()));
        account = repositoryAccountPort.save(account);
        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountById(Integer id) {
        Account account = repositoryAccountPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public void deleteAccountByID(Integer id) {
        Account existingAccount = repositoryAccountPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));

        existingAccount.setActive(false);
        repositoryAccountPort.save(existingAccount);
    }

    @Override
    public AccountResponse updateAccountByID(Integer id, AccountRequest accountRequest) {
        Account existingAccount = repositoryAccountPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));

        existingAccount.setName(accountRequest.getName());
        existingAccount.setEmail(accountRequest.getEmail());
        if (accountRequest.getProfilePhotoUrl() != null){
            existingAccount.setProfilePhotoUrl(accountRequest.getProfilePhotoUrl().toString());
        }

        Account savedAccount = repositoryAccountPort.save(existingAccount);
        return accountMapper.accountToAccountResponse(savedAccount);
    }

}
