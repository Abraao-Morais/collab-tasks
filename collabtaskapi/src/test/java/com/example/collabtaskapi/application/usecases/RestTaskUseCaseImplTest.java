package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestTaskUseCaseImplTest {

    @Mock
    private RepositoryTaskPort repositoryTaskPort;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private RestTaskUseCaseImpl restTaskUseCase;

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

        when(repositoryTaskPort.findById(1)).thenReturn(Optional.of(task));
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = restTaskUseCase.getTaskById(1);

        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFoundById() {
        var id = 99;
        when(repositoryTaskPort.findById(id)).thenThrow(new EntityNotFoundException("Task not found with id 99"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restTaskUseCase.getTaskById(id));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldDeleteTaskByIdWhenExists() {
        Task task = TaskFactory.taskFactory();

        when(repositoryTaskPort.findById(task.getId())).thenReturn(Optional.of(task));
        doNothing().when(repositoryTaskPort).delete(any());

        restTaskUseCase.deleteTaskById(1);

        verify(repositoryTaskPort).delete(task);
    }

    @Test
    void shouldThrowExceptionWhenDeleteTaskNotFound() {
        var id = 99;
        when(repositoryTaskPort.findById(id)).thenThrow(new EntityNotFoundException("Task not found with id " + id));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restTaskUseCase.deleteTaskById(id));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldUpdateTaskWhenExists() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(repositoryTaskPort.findById(1)).thenReturn(Optional.of(task));
        when(repositoryTaskPort.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = restTaskUseCase.updateTaskById(1, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdateTaskNotFound() {
        var id = 99;
        when(repositoryTaskPort.findById(id)).thenThrow(new EntityNotFoundException("Task not found with id 99"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restTaskUseCase.updateTaskById(id, any()));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldReturnListOfAllTasks() {
        List<Task> tasks = Arrays.asList(TaskFactory.taskFactory(), TaskFactory.taskFactory());
        List<TaskResponse> responses = Arrays.asList(TaskFactory.taskResponseFactory(), TaskFactory.taskResponseFactory());

        when(repositoryTaskPort.findAll()).thenReturn(tasks);
        when(taskMapper.taskToTaskResponse(any())).thenReturn(TaskFactory.taskResponseFactory());

        List<TaskResponse> result = restTaskUseCase.listAllTasks();

        assertEquals(2, result.size());
        verify(repositoryTaskPort).findAll();
    }

    @Test
    void shouldReturnListOfFilteredTasks() {
        Integer assignedTo = 1;
        Status status = Status.TO_DO;
        Priority priority = Priority.HIGH;
        LocalDate dueBefore = LocalDate.now();

        List<Task> filteredTasks = Arrays.asList(TaskFactory.taskFactory(), TaskFactory.taskFactory());
        List<TaskResponse> filteredResponses = Arrays.asList(TaskFactory.taskResponseFactory(), TaskFactory.taskResponseFactory());

        when(repositoryTaskPort.findByFilters(eq(assignedTo), eq(status), eq(priority), eq(dueBefore.plusDays(1))))
                .thenReturn(filteredTasks);
        when(taskMapper.taskToTaskResponse(any())).thenReturn(TaskFactory.taskResponseFactory());

        List<TaskResponse> result = restTaskUseCase.listTasksByFilters(assignedTo, status, priority, dueBefore);

        assertEquals(2, result.size());
        verify(repositoryTaskPort).findByFilters(eq(assignedTo), eq(status), eq(priority), eq(dueBefore.plusDays(1)));
    }
}
