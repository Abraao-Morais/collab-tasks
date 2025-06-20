package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.Account;

import java.util.List;
import java.util.Optional;

public interface RepositoryAccountPort {

    Optional<Account> findById(Integer id);
    Optional<Account> findByName(String name);
    List<Account> findAll();
    Account save(Account account);

}