package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaAccountRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryAccountPortImpl implements RepositoryAccountPort {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;

    public RepositoryAccountPortImpl(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account findById(Integer id) {
        JpaAccountEntity accountEntity = this.jpaAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
        return accountMapper.jpaAccountEntityToAccount(accountEntity);
    }

    @Override
    public Account findByName(String name) {
        JpaAccountEntity accountEntity = this.jpaAccountRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with name " + name));
        return accountMapper.jpaAccountEntityToAccount(accountEntity);
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
        accountEntity = this.jpaAccountRepository.save(accountEntity);
        return accountMapper.jpaAccountEntityToAccount(accountEntity);
    }

    @Override
    public void delete(Integer id) {
        JpaAccountEntity entity = jpaAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + id));
        jpaAccountRepository.delete(entity);
    }
}
