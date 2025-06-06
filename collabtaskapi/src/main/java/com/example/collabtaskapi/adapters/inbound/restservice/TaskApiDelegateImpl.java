package com.example.collabtaskapi.adapters.inbound.restservice;

import com.example.collabtaskapi.application.service.TaskServiceImpl;
import com.example.collabtaskapi.controller.TaskApiDelegate;
import com.example.collabtaskapi.dto.TaskRequest;
import com.example.collabtaskapi.dto.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Component
public class TaskApiDelegateImpl implements TaskApiDelegate {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    public void setTaskServiceImpl(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }
    @Override
    public ResponseEntity<TaskResponse> createNewTask(TaskRequest taskRequest) {
        TaskResponse createdTask = taskServiceImpl.createNewTask(taskRequest);

        if (createdTask == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .created(URI.create("/task/" + createdTask.getId()))
                .body(createdTask);
    }

    @Override
    public ResponseEntity<Void> deleteTaskById(Integer id){
        taskServiceImpl.deleteTaskByID(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Integer id){
        TaskResponse taskResponse = taskServiceImpl.getTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> taskGet(Integer assignedTo){
        List<TaskResponse> responseList = taskServiceImpl.findAllByAccountId(assignedTo);
        if (responseList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTaskById(Integer id, TaskRequest taskRequest){
        TaskResponse taskResponse = taskServiceImpl.updateTaskById(id, taskRequest);
        return ResponseEntity.ok(taskResponse);
    }
}
