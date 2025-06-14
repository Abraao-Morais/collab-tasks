package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTaskPort;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import com.example.collabtaskapi.factory.TaskFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestTaskUseCaseImplTest {

    @Mock
    private RepositoryTaskPort repositoryTaskPort;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private RestTaskUseCaseImpl restTaskUseCase;

    @Test
    void shouldReturnListOfTaskResponsesByAccountId() {
        Task task1 = TaskFactory.taskFactory();
        Task task2 = TaskFactory.taskFactory();
        TaskResponse response1 = TaskFactory.taskResponseFactory();
        TaskResponse response2 = TaskFactory.taskResponseFactory();

        when(repositoryTaskPort.findAllByAccountId(1)).thenReturn(Arrays.asList(task1, task2));
        when(taskMapper.taskToTaskResponse(task1)).thenReturn(response1);
        when(taskMapper.taskToTaskResponse(task2)).thenReturn(response2);

        List<TaskResponse> result = restTaskUseCase.findAllByAccountId(1);

        assertEquals(2, result.size());
        verify(repositoryTaskPort).findAllByAccountId(1);
    }

    @Test
    void shouldCreateAndReturnNewTaskResponse() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(taskMapper.taskRequestToTask(request)).thenReturn(task);
        when(repositoryTaskPort.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = restTaskUseCase.createNewTask(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(repositoryTaskPort).save(task);
    }

    @Test
    void shouldReturnTaskById() {
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(repositoryTaskPort.findById(1)).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = restTaskUseCase.getTaskById(1);

        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFoundById() {
        when(repositoryTaskPort.findById(99)).thenThrow(new EntityNotFoundException(" "));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restTaskUseCase.getTaskById(99));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldDeleteTaskByIdWhenExists() {
        Task task = TaskFactory.taskFactory();

        when(repositoryTaskPort.findById(1)).thenReturn(null);

        restTaskUseCase.deleteTaskByID(1);

        verify(repositoryTaskPort).delete(1);
    }

    @Test
    void shouldThrowExceptionWhenDeleteTaskNotFound() {
        when(repositoryTaskPort.findById(99)).thenThrow(new EntityNotFoundException(" "));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restTaskUseCase.deleteTaskByID(99));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldUpdateTaskWhenExists() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(repositoryTaskPort.findById(1)).thenReturn(task);
        when(repositoryTaskPort.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = restTaskUseCase.updateTaskById(1, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdateTaskNotFound() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        when(repositoryTaskPort.findById(99)).thenThrow(new EntityNotFoundException(" "));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restTaskUseCase.updateTaskById(99, request));

        assertEquals("Task not found with id 99", exception.getMessage());
    }
}
