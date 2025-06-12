package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;

import java.util.List;

public interface TaskUseCase {

    public List<TaskResponse> findAllByAccountId(Integer accountId);
    public TaskResponse createNewTask(TaskRequest taskRequest);
    public TaskResponse getTaskById(Integer id);
    public void deleteTaskByID(Integer id);
    public TaskResponse updateTaskById(Integer id, TaskRequest taskRequest);
}
