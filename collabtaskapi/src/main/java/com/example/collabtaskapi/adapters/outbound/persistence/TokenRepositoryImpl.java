package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTokenEntity;
import com.example.collabtaskapi.application.ports.outbound.TokenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TokenRepositoryImpl implements TokenRepository {


    @Override
    public List<JpaTokenEntity> findAllValidTokenByAccountId(Integer accountId) {
        return List.of();
    }

    @Override
    public Optional<JpaTokenEntity> findByToken(String token) {
        return Optional.empty();
    }
}
