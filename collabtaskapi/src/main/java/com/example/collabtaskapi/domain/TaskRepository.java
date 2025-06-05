package com.example.collabtaskapi.domain;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(Integer id);
    List<Task> findAllByAccountId(Integer accountId);
    Task save(Task task);
    void delete(Integer id);
}
