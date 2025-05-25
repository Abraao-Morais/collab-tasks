package com.example.collabtaskapi.adapters.outbound.repository;

import com.example.collabtaskapi.adapters.outbound.entities.JpaAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAccountRepository extends JpaRepository<JpaAccountEntity, Integer> {

}
