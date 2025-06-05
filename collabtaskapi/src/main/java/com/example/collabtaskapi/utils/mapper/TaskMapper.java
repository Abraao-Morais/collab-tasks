package com.example.collabtaskapi.utils.mapper;

import com.example.collabtaskapi.adapters.outbound.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.dto.TaskRequest;
import com.example.collabtaskapi.dto.TaskResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task jpaTaskEntityToTask(JpaTaskEntity jpaTaskEntity);
    Task taskRequestToTask(TaskRequest taskRequest);
    TaskResponse taskToTaskResponse(Task task);
}
