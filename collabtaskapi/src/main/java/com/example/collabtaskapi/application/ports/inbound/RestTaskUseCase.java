package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;

import java.time.LocalDate;
import java.util.List;

public interface RestTaskUseCase {
    TaskResponse createNewTask(TaskRequest request);
    TaskResponse getTaskById(Integer id);
    TaskResponse updateTaskById(Integer id, TaskRequest request);
    void deleteTaskById(Integer id);
    List<TaskResponse> listAllTasks();
    List<TaskResponse> listTasksByFilters(Integer assignedTo, Status status, Priority priority, LocalDate dueBefore);
}
