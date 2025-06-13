package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.controllers.TaskApiDelegate;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
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
        restTaskUseCase.deleteTaskByID(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Integer id) {
        TaskResponse taskResponse = restTaskUseCase.getTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> taskGet(Integer assignedTo) {
        List<TaskResponse> responseList = restTaskUseCase.findAllByAccountId(assignedTo);
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
