package com.example.collabtaskapi.adapters.outbound.persistence.repositories;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAccountRepository extends JpaRepository<JpaAccountEntity, Integer> {

    Optional<JpaAccountEntity> findByName(String name);

}
