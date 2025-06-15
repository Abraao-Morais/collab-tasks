package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.Token;

import java.util.List;
import java.util.Optional;

public interface RepositoryTokenPort {

    List<Token> findAllValidTokenByAccountId(Integer accountId);
    Optional<Token> findByToken(String token);
    Token save(Token token);
    void saveAll(List<Token> tokens);
}
