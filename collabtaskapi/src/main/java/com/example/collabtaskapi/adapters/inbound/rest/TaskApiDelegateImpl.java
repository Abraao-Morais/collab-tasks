package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.TaskUseCase;
import com.example.collabtaskapi.controllers.TaskApiDelegate;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class TaskApiDelegateImpl implements TaskApiDelegate {

    private final TaskUseCase taskUseCase;

    public TaskApiDelegateImpl(TaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    @Override
    public ResponseEntity<TaskResponse> createNewTask(TaskRequest taskRequest) {
        TaskResponse createdTask = taskUseCase.createNewTask(taskRequest);

        if (createdTask == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .created(URI.create("/task/" + createdTask.getId()))
                .body(createdTask);
    }

    @Override
    public ResponseEntity<Void> deleteTaskById(Integer id) {
        taskUseCase.deleteTaskByID(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Integer id) {
        TaskResponse taskResponse = taskUseCase.getTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> taskGet(Integer assignedTo) {
        List<TaskResponse> responseList = taskUseCase.findAllByAccountId(assignedTo);
        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTaskById(Integer id, TaskRequest taskRequest) {
        TaskResponse taskResponse = taskUseCase.updateTaskById(id, taskRequest);
        return ResponseEntity.ok(taskResponse);
    }
}
