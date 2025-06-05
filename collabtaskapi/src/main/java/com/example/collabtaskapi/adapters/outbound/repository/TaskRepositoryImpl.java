package com.example.collabtaskapi.adapters.outbound.repository;

import com.example.collabtaskapi.adapters.outbound.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.TaskRepository;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mapper.AccountMapper;
import com.example.collabtaskapi.utils.mapper.TaskMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final JpaTaskRepository jpaTaskRepository;
    private final TaskMapper taskMapper;
    private final AccountMapper accountMapper;

    public TaskRepositoryImpl(JpaTaskRepository jpaTaskRepository, TaskMapper taskMapper, AccountMapper accountMapper) {
        this.jpaTaskRepository = jpaTaskRepository;
        this.taskMapper = taskMapper;
        this.accountMapper = accountMapper;
    }
    @Override
    public Optional<Task> findById(Integer id) {
        Optional<JpaTaskEntity> taskEntity = this.jpaTaskRepository.findById(id);
        return taskEntity.map(taskMapper::jpaTaskEntityToTask);
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
    public void delete(Integer id) {
        JpaTaskEntity jpaTaskEntity = this.jpaTaskRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
        jpaTaskRepository.delete(jpaTaskEntity);
    }
}
