package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.dto.AccountRequest;
import com.example.collabtaskapi.dto.AccountResponse;
import com.example.collabtaskapi.dto.TaskRequest;
import com.example.collabtaskapi.dto.TaskResponse;

import java.util.List;
import java.util.Optional;

public interface TaskUseCase {

    public List<TaskResponse> findAllByAccountId(Integer accountId);
    public TaskResponse createNewTask(TaskRequest taskRequest);
    public TaskResponse getTaskById(Integer id);
    public void deleteTaskByID(Integer id);
    public TaskResponse updateTaskById(Integer id, TaskRequest taskRequest);
}
