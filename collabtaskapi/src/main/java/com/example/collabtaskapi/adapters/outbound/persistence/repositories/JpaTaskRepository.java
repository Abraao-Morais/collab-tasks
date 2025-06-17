package com.example.collabtaskapi.adapters.outbound.persistence.repositories;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaTaskRepository extends JpaRepository<JpaTaskEntity, Integer> {

    List<JpaTaskEntity> findAllByAccountId(Integer accountId);

    @Query("SELECT t FROM JpaTaskEntity t " +
            "WHERE t.account.id = :accountId " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND (:dueBefore IS NULL OR t.dueDate <= :dueBefore)")
    List<JpaTaskEntity> findByFilters(
            @Param("accountId") Integer accountId,
            @Param("status") Status status,
            @Param("priority") Priority priority,
            @Param("dueBefore") LocalDate dueBefore
    );
}