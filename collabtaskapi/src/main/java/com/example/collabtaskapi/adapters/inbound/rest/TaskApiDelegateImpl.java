package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.controllers.TaskApiDelegate;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class TaskApiDelegateImpl implements TaskApiDelegate {

    private final RestTaskUseCase restTaskUseCase;

    public TaskApiDelegateImpl(RestTaskUseCase restTaskUseCase) {
        this.restTaskUseCase = restTaskUseCase;
    }

    @Override
    public ResponseEntity<TaskResponse> createNewTask(TaskRequest taskRequest) {
        TaskResponse createdTask = restTaskUseCase.createNewTask(taskRequest);

        if (createdTask == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .created(URI.create("/task/" + createdTask.getId()))
                .body(createdTask);
    }

    @Override
    public ResponseEntity<Void> deleteTaskById(Integer id) {
        restTaskUseCase.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Integer id) {
        TaskResponse taskResponse = restTaskUseCase.getTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = restTaskUseCase.listAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getTasksByAssignedTo(Integer assignedTo,
                                                                   String status,
                                                                   String priority,
                                                                   Date dueBefore) {
        LocalDate dueBeforeDate = null;
        if (dueBefore != null) {
            dueBeforeDate = dueBefore.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        // Conversão segura de status
        Status statusEnum = null;
        if (status != null && !status.isBlank()) {
            try {
                statusEnum = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null); // ou resposta com mensagem
            }
        }

        // Conversão segura de priority
        Priority priorityEnum = null;
        if (priority != null && !priority.isBlank()) {
            try {
                priorityEnum = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null); // ou resposta com mensagem
            }
        }

        List<TaskResponse> responseList = restTaskUseCase.listTasksByFilters(
                assignedTo, statusEnum, priorityEnum, dueBeforeDate
        );

        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTaskById(Integer id, TaskRequest taskRequest) {
        TaskResponse taskResponse = restTaskUseCase.updateTaskById(id, taskRequest);
        return ResponseEntity.ok(taskResponse);
    }
}
