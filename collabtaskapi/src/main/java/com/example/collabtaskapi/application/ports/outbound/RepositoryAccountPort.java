package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.Account;

import java.util.List;

public interface RepositoryAccountPort {

    Account findById(Integer id);
    Account findByName(String name);
    List<Account> findAll();
    Account save(Account account);
    void delete(Integer id);

}