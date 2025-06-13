package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTokenEntity;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RepositoryTokenPortImpl implements RepositoryTokenPort {

    @Override
    public List<JpaTokenEntity> findAllValidTokenByAccountId(Integer accountId) {
        return List.of();
    }

    @Override
    public Optional<JpaTokenEntity> findByToken(String token) {
        return Optional.empty();
    }
}
