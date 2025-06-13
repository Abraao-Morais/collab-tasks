package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.domain.Token;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RepositoryTokenPortImpl implements RepositoryTokenPort {

    @Override
    public List<Token> findAllValidTokenByAccountId(Integer accountId) {
        return List.of();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public Token save(Token token) {
        return null;
    }
}
