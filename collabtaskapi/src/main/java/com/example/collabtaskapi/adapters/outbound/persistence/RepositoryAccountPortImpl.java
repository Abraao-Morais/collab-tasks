package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaAccountRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RepositoryAccountPortImpl implements RepositoryAccountPort {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountMapper accountMapper;

    public RepositoryAccountPortImpl(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return jpaAccountRepository.findById(id).map(accountMapper::jpaAccountEntityToAccount);
    }

    @Override
    public Optional<Account> findByName(String name) {
        return jpaAccountRepository.findByName(name).map(accountMapper::jpaAccountEntityToAccount);
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

}
