package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.Task;

import java.util.List;

public interface RepositoryTaskPort {

    Task findById(Integer id);
    List<Task> findAllByAccountId(Integer accountId);
    Task save(Task task);
    void delete(Integer id);

}
