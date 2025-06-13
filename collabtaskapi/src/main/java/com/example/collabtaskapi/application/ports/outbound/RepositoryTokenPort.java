package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTokenEntity;

import java.util.List;
import java.util.Optional;

public interface RepositoryTokenPort {

    List<JpaTokenEntity> findAllValidTokenByAccountId(Integer accountId);
    Optional<JpaTokenEntity> findByToken(String token);
}
