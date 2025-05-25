package com.example.collabtaskapi.domain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(Integer id);
    List<Account> findAll();
    Account save(Account account);
    void delete(Integer id);
}