package com.example.collabtaskapi.adapters.outbound.persistence.repositories;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaTokenRepository extends JpaRepository<JpaTokenEntity, Integer> {

    @Query("""
            select t from JpaTokenEntity t inner join JpaAccountEntity a on t.account.id = a.id
            where a.id = :accountId and t.revoked = false
            """)
    List<JpaTokenEntity> findAllValidTokenByAccountId(@Param("accountId") Integer accountId);

    Optional<JpaTokenEntity> findByToken(String token);
}
