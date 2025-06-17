package com.example.collabtaskapi.utils.mappers;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task jpaTaskEntityToTask(JpaTaskEntity jpaTaskEntity);
    Task taskRequestToTask(TaskRequest taskRequest);
    @Mappings({
            @Mapping(source = "account.id", target = "accountId")
    })
    TaskResponse taskToTaskResponse(Task task);
    JpaTaskEntity taskTTaskEntity(Task task);

}
