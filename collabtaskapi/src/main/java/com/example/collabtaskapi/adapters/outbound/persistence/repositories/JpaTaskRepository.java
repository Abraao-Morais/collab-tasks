package com.example.collabtaskapi.adapters.outbound.persistence.repositories;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRepository extends JpaRepository<JpaTaskEntity, Integer> {

    List<JpaTaskEntity> findAllByAccountId(Integer accountId);

}
