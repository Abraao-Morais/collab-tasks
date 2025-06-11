package com.example.collabtaskapi.application.services;

import com.example.collabtaskapi.application.ports.inbound.TaskUseCase;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.application.ports.outbound.TaskRepository;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, AccountMapper accountMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskResponse> findAllByAccountId(Integer accountId) {
        return taskRepository.findAllByAccountId(accountId).stream().
                map(taskMapper::taskToTaskResponse).toList();
    }

    @Override
    public TaskResponse createNewTask(TaskRequest taskRequest) {
        Task task = taskMapper.taskRequestToTask(taskRequest);

        Account account = new Account();
        account.setId(taskRequest.getAccountId());
        task.setAccount(account);

        task = taskRepository.save(task);
        return taskMapper.taskToTaskResponse(task);
    }
    @Override
    public TaskResponse getTaskById(Integer id) {
        Task task = taskRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id ));
        return taskMapper.taskToTaskResponse(task);
    }

    @Override
    public void deleteTaskByID(Integer id) {
        Task task = taskRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("Task not found with id " + id ));
        taskRepository.delete(id);
    }

    @Override
    public TaskResponse updateTaskById(Integer id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id ));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(Status.valueOf(taskRequest.getStatus().name()));

        Task savedTask = taskRepository.save(task);
        return taskMapper.taskToTaskResponse(savedTask);
    }
}
