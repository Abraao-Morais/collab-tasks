package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaAccountRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.application.ports.outbound.AccountRepository;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;

import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<Account> findById(Integer id) {
        Optional<JpaAccountEntity> accountEntity = this.jpaAccountRepository.findById(id);
        return accountEntity.map(accountMapper::jpaAccountEntityToAccount);
    }

    @Override
    public Optional<Account> findByName(String name) {
        Optional<JpaAccountEntity> accountEntity = this.jpaAccountRepository.findByName(name);
        return accountEntity.map(accountMapper::jpaAccountEntityToAccount);
    }

    @Override
    public List<Account> findAll() {
        return this.jpaAccountRepository.findAll()
                .stream()
                .map(accountMapper::jpaAccountEntityToAccount).toList();
    }

    @Override
    public Account save(Account account) {
        JpaAccountEntity accountEntity = new JpaAccountEntity(account);
        this.jpaAccountRepository.save(accountEntity);
        return accountMapper.jpaAccountEntityToAccount(accountEntity);
    }

    @Override
    public void delete(Integer id) {
        JpaAccountEntity entity = jpaAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
        jpaAccountRepository.delete(entity);
    }
}
