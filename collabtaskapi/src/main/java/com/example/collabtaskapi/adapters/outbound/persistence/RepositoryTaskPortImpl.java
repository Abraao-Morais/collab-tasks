package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaTaskRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTaskPort;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepositoryTaskPortImpl implements RepositoryTaskPort {

    private final JpaTaskRepository jpaTaskRepository;
    private final TaskMapper taskMapper;
    private final AccountMapper accountMapper;

    public RepositoryTaskPortImpl(JpaTaskRepository jpaTaskRepository, TaskMapper taskMapper, AccountMapper accountMapper) {
        this.jpaTaskRepository = jpaTaskRepository;
        this.taskMapper = taskMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return jpaTaskRepository.findById(id).map(taskMapper::jpaTaskEntityToTask);
    }

    @Override
    public List<Task> findAllByAccountId(Integer accountId) {
        return jpaTaskRepository.findAllByAccountId(accountId).stream()
                .map(taskMapper::jpaTaskEntityToTask)
                .collect(Collectors.toList());
    }

    @Override
    public Task save(Task task) {
        Account account = task.getAccount();
        JpaTaskEntity jpaTaskEntity = new JpaTaskEntity(task, accountMapper.accountToJpaAccountEntity(account));
        jpaTaskEntity = jpaTaskRepository.save(jpaTaskEntity);
        return taskMapper.jpaTaskEntityToTask(jpaTaskEntity);
    }


    @Override
    public void delete(Task task) {
        jpaTaskRepository.delete(taskMapper.taskTTaskEntity(task));
    }

    @Override
    public List<Task> findByFilters(Integer accountId, Status status, Priority priority, LocalDate dueBefore) {
        List<JpaTaskEntity> entities = jpaTaskRepository.findByFilters(accountId, status, priority, dueBefore);

        return entities.stream()
                .map(taskMapper::jpaTaskEntityToTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll() {
        return this.jpaTaskRepository.findAll()
                .stream()
                .map(taskMapper::jpaTaskEntityToTask).toList();
    }

}
