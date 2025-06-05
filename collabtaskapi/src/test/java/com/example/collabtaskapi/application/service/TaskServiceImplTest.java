package com.example.collabtaskapi.application.service;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.TaskRepository;
import com.example.collabtaskapi.dto.TaskRequest;
import com.example.collabtaskapi.dto.TaskResponse;
import com.example.collabtaskapi.factory.TaskFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mapper.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnListOfTaskResponsesByAccountId() {
        Task task1 = TaskFactory.taskFactory();
        Task task2 = TaskFactory.taskFactory();
        TaskResponse response1 = TaskFactory.taskResponseFactory();
        TaskResponse response2 = TaskFactory.taskResponseFactory();

        when(taskRepository.findAllByAccountId(1)).thenReturn(Arrays.asList(task1, task2));
        when(taskMapper.taskToTaskResponse(task1)).thenReturn(response1);
        when(taskMapper.taskToTaskResponse(task2)).thenReturn(response2);

        List<TaskResponse> result = taskService.findAllByAccountId(1);

        assertEquals(2, result.size());
        verify(taskRepository).findAllByAccountId(1);
    }

    @Test
    void shouldCreateAndReturnNewTaskResponse() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(taskMapper.taskRequestToTask(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = taskService.createNewTask(request);

        assertNotNull(result);
        assertEquals(response, result);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldReturnTaskById() {
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = taskService.getTaskById(1);

        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFoundById() {
        when(taskRepository.findById(99)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> taskService.getTaskById(99));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldDeleteTaskByIdWhenExists() {
        Task task = TaskFactory.taskFactory();

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        taskService.deleteTaskByID(1);

        verify(taskRepository).delete(1);
    }

    @Test
    void shouldThrowExceptionWhenDeleteTaskNotFound() {
        when(taskRepository.findById(99)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> taskService.deleteTaskByID(99));

        assertEquals("Task not found with id 99", exception.getMessage());
    }

    @Test
    void shouldUpdateTaskWhenExists() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        Task task = TaskFactory.taskFactory();
        TaskResponse response = TaskFactory.taskResponseFactory();

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.taskToTaskResponse(task)).thenReturn(response);

        TaskResponse result = taskService.updateTaskById(1, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdateTaskNotFound() {
        TaskRequest request = TaskFactory.taskRequestFactory();
        when(taskRepository.findById(99)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> taskService.updateTaskById(99, request));

        assertEquals("Task not found with id 99", exception.getMessage());
    }
}
