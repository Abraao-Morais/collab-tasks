package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTaskPort;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import com.example.collabtaskapi.utils.mappers.TaskMapper;

import java.util.List;

public class RestTaskUseCaseImpl implements RestTaskUseCase {

    private final RepositoryTaskPort repositoryTaskPort;
    private final TaskMapper taskMapper;

    public RestTaskUseCaseImpl(RepositoryTaskPort repositoryTaskPort, TaskMapper taskMapper) {
        this.repositoryTaskPort = repositoryTaskPort;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskResponse> findAllByAccountId(Integer accountId) {
        return repositoryTaskPort.findAllByAccountId(accountId).stream().
                map(taskMapper::taskToTaskResponse).toList();
    }

    @Override
    public TaskResponse createNewTask(TaskRequest taskRequest) {
        Task task = taskMapper.taskRequestToTask(taskRequest);

        Account account = new Account();
        account.setId(taskRequest.getAccountId());
        task.setAccount(account);

        task = repositoryTaskPort.save(task);
        return taskMapper.taskToTaskResponse(task);
    }

    @Override
    public TaskResponse getTaskById(Integer id) {
        Task task = repositoryTaskPort.findById(id);
        return taskMapper.taskToTaskResponse(task);
    }

    @Override
    public void deleteTaskByID(Integer id) {
        Task task = repositoryTaskPort.findById(id);
        repositoryTaskPort.delete(id);
    }

    @Override
    public TaskResponse updateTaskById(Integer id, TaskRequest taskRequest) {
        Task task = repositoryTaskPort.findById(id);

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(Status.valueOf(taskRequest.getStatus().name()));

        Task savedTask = repositoryTaskPort.save(task);
        return taskMapper.taskToTaskResponse(savedTask);
    }
}
