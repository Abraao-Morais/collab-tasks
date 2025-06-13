package com.example.collabtaskapi.utils.mappers;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import com.example.collabtaskapi.factory.TaskFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void shouldMapJpaTaskEntityToTask() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();

        Task task = taskMapper.jpaTaskEntityToTask(entity);

        assertNotNull(task);
        assertEquals(entity.getId(), task.getId());
        assertEquals(entity.getTitle(), task.getTitle());
        assertEquals(entity.getDescription(), task.getDescription());
        assertEquals(entity.getStatus().name(), task.getStatus().name());
        assertEquals(entity.getAccount().getId(), task.getAccount().getId());
    }

    @Test
    public void shouldMapTaskRequestToTask() {
        TaskRequest request = TaskFactory.taskRequestFactory();

        Task task = taskMapper.taskRequestToTask(request);

        assertNotNull(task);
        assertEquals(request.getTitle(), task.getTitle());
        assertEquals(request.getDescription(), task.getDescription());
        assertEquals(request.getStatus().name(), task.getStatus().name());
    }

    @Test
    public void shouldMapTaskToTaskResponse() {
        Task task = TaskFactory.taskFactory();

        TaskResponse response = taskMapper.taskToTaskResponse(task);

        assertNotNull(response);
        assertEquals(task.getId(), response.getId());
        assertEquals(task.getTitle(), response.getTitle());
        assertEquals(task.getDescription(), response.getDescription());
        assertEquals(task.getStatus().name(), response.getStatus().name());
    }
}
