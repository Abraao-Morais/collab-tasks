package com.example.collabtaskapi.utils.mappers;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task jpaTaskEntityToTask(JpaTaskEntity jpaTaskEntity);
    Task taskRequestToTask(TaskRequest taskRequest);
    TaskResponse taskToTaskResponse(Task task);
    JpaTaskEntity taskTTaskEntity(Task task);

}
