package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;

import java.util.List;

public interface TaskUseCase {

    List<TaskResponse> findAllByAccountId(Integer accountId);
    TaskResponse createNewTask(TaskRequest taskRequest);
    TaskResponse getTaskById(Integer id);
    void deleteTaskByID(Integer id);
    TaskResponse updateTaskById(Integer id, TaskRequest taskRequest);

}
