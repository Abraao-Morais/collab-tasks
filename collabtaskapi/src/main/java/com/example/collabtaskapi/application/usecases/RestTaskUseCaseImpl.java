package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTaskPort;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.TaskMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RestTaskUseCaseImpl implements RestTaskUseCase {

    private final RepositoryTaskPort repositoryTaskPort;
    private final TaskMapper taskMapper;

    public RestTaskUseCaseImpl(RepositoryTaskPort repositoryTaskPort, TaskMapper taskMapper) {
        this.repositoryTaskPort = repositoryTaskPort;
        this.taskMapper = taskMapper;
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
        Task task = repositoryTaskPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
        return taskMapper.taskToTaskResponse(task);
    }

    @Override
    public TaskResponse updateTaskById(Integer id, TaskRequest taskRequest) {
        Task task = repositoryTaskPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(Status.valueOf(taskRequest.getStatus().name()));

        Task savedTask = repositoryTaskPort.save(task);
        return taskMapper.taskToTaskResponse(savedTask);
    }

    @Override
    public void deleteTaskById(Integer id) {
        Task task = repositoryTaskPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
        repositoryTaskPort.delete(id);
    }

    @Override
    public List<TaskResponse> listAllTasks() {
        List<Task> tasks = repositoryTaskPort.findAll();
        return taskMapper.tasksToTaskResponses(tasks);
    }

    @Override
    public List<TaskResponse> listTasksByFilters(Integer assignedTo, Status status, Priority priority, LocalDate dueBefore) {
        LocalDateTime dueBeforePlusOne = null;
        if (dueBefore != null) {
            dueBeforePlusOne = dueBefore.plusDays(1).atStartOfDay();
        }
        List<Task> filteredTasks = repositoryTaskPort.findByFilters(assignedTo, status, priority, LocalDate.from(dueBeforePlusOne));
        return taskMapper.tasksToTaskResponses(filteredTasks);
    }
}