package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.Token;

import java.util.List;

public interface RepositoryTokenPort {

    List<Token> findAllValidTokenByAccountId(Integer accountId);
    Token findByToken(String token);
    Token save(Token token);
    List<Token> saveAll(List<Token> tokens);
}
