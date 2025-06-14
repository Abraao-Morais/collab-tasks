package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTokenEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaTokenRepository;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.TokenMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryTokenPortImpl implements RepositoryTokenPort {

    private final JpaTokenRepository jpaTokenRepository;
    private final TokenMapper tokenMapper;

    public RepositoryTokenPortImpl(JpaTokenRepository jpaTokenRepository, TokenMapper tokenMapper) {
        this.jpaTokenRepository = jpaTokenRepository;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public List<Token> findAllValidTokenByAccountId(Integer accountId) {
        return jpaTokenRepository.findAllValidTokenByAccountId(accountId).stream()
                .map(tokenMapper::jpaTokenEntityToToken)
                .toList();
    }

    @Override
    public Token findByToken(String token) {
        JpaTokenEntity tokenEntity = jpaTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found with token " + token));
        return tokenMapper.jpaTokenEntityToToken(tokenEntity);
    }

    @Override
    public Token save(Token token) {
        var entity = jpaTokenRepository.save(tokenMapper.tokenToJpaTokenEntity(token));
        return tokenMapper.jpaTokenEntityToToken(entity);
    }

    @Override
    public List<Token>  saveAll(List<Token> tokens) {
        var entities = jpaTokenRepository.saveAll(tokens.stream().map(tokenMapper::tokenToJpaTokenEntity).toList());
        return entities.stream()
                .map(tokenMapper::jpaTokenEntityToToken)
                .toList();
    }
}
