package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.application.usecases.RestTaskUseCaseImpl;
import com.example.collabtaskapi.controllers.TaskApiController;
import com.example.collabtaskapi.controllers.TaskApiDelegate;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import com.example.collabtaskapi.factory.TaskFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskApiController.class)
@Import(TaskApiDelegateImplTest.TestConfig.class)
public class TaskApiDelegateImplTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RestTaskUseCase restTaskUseCase() {
            return Mockito.mock(RestTaskUseCaseImpl.class);
        }

        @Bean
        public TaskApiDelegate taskApiDelegate(RestTaskUseCase restTaskUseCase) {
            return new TaskApiDelegateImpl(restTaskUseCase);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTaskUseCase restTaskUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskRequest taskRequest;
    private TaskResponse taskResponse;
    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 999;
    private static final String BASE_PATH = "/task";

    @BeforeEach
    void setup() {
        taskRequest = TaskFactory.taskRequestFactory();
        taskResponse = TaskFactory.taskResponseFactory();
    }

    @Test
    void shouldCreateNewTask() throws Exception {
        when(restTaskUseCase.createNewTask(any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_PATH + "/" + VALID_ID))
                .andExpect(jsonPath("$.title").value("Tarefa 1"));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        when(restTaskUseCase.getTaskById(VALID_ID)).thenReturn(taskResponse);

        mockMvc.perform(get(BASE_PATH + "/" + VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID));
    }

    @Test
    void shouldReturn404WithMessageWhenTaskNotFound() throws Exception {
        when(restTaskUseCase.getTaskById(INVALID_ID))
                .thenThrow(new EntityNotFoundException("Task not found with id " + INVALID_ID));

        mockMvc.perform(get(BASE_PATH + "/" + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id " + INVALID_ID));
    }

    @Test
    void shouldReturnTasksByFilters() throws Exception {
        when(restTaskUseCase.listTasksByFilters(eq(1), any(), any(), any()))
                .thenReturn(List.of(taskResponse));

        mockMvc.perform(get(BASE_PATH + "/assignedTo")
                        .param("assignedTo", "1")
                        .param("status", "TO_DO")
                        .param("priority", "HIGH")
                        .param("dueBefore", "2025-06-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Tarefa 1"));
    }

    @Test
    void shouldReturnNoContentWhenGetAllTasksReturnsEmptyList() throws Exception {
        when(restTaskUseCase.listAllTasks()).thenReturn(List.of());

        mockMvc.perform(get(BASE_PATH + "/all"))
                .andExpect(status().isNoContent());
    }


    @Test
    void shouldReturnNoContentWhenNoTasksMatchFilters() throws Exception {
        when(restTaskUseCase.listTasksByFilters(eq(2), any(), any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get(BASE_PATH + "/assignedTo")
                        .param("assignedTo", "2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenInvalidStatusIsProvided() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/assignedTo")
                        .param("assignedTo", "1")
                        .param("status", "INVALID_STATUS"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenInvalidPriorityIsProvided() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/assignedTo")
                        .param("assignedTo", "1")
                        .param("priority", "INVALID_PRIORITY"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        when(restTaskUseCase.updateTaskById(eq(VALID_ID), any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(put(BASE_PATH + "/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID));
    }

    @Test
    void shouldReturn404WhenUpdateNonExistingTask() throws Exception {
        when(restTaskUseCase.updateTaskById(eq(INVALID_ID), any(TaskRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put(BASE_PATH + "/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/" + VALID_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeleteNonExistingTask() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(restTaskUseCase).deleteTaskById(INVALID_ID);

        mockMvc.perform(delete(BASE_PATH + "/" + INVALID_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenCreateNewTaskWithInvalidStatus() throws Exception {
        String invalidJson = "{ \"title\": \"Tarefa Inválida\", \"description\": \"desc\", \"status\": \"INVALID\", \"accountId\": 1 }";

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenCreateNewTaskWithNegativeAccountId() throws Exception {
        taskRequest.setAccountId(-5);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isBadRequest());
    }
}
