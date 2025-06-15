package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RepositoryTaskPort {

    Optional<Task> findById(Integer id);
    List<Task> findAllByAccountId(Integer accountId);
    Task save(Task task);
    void delete(Integer id);
    List<Task> findByFilters(Integer accountId, Status status, Priority priority, LocalDate dueBefore);
    List<Task> findAll();
}
