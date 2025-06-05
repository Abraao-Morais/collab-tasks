package com.example.collabtaskapi.adapters.inbound.restservice;

import com.example.collabtaskapi.application.service.TaskServiceImpl;
import com.example.collabtaskapi.controller.TaskApiController;
import com.example.collabtaskapi.controller.TaskApiDelegate;
import com.example.collabtaskapi.dto.TaskRequest;
import com.example.collabtaskapi.dto.TaskResponse;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskApiController.class)
@Import(TaskApiDelegateImplTest.TestConfig.class)
public class TaskApiDelegateImplTest {

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 999;
    private static final String BASE_PATH = "/task";

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TaskServiceImpl taskServiceImpl() {
            return Mockito.mock(TaskServiceImpl.class);
        }

        @Bean
        public TaskApiDelegate taskApiDelegate(TaskServiceImpl taskServiceImpl) {
            TaskApiDelegateImpl delegate = new TaskApiDelegateImpl();
            delegate.setTaskServiceImpl(taskServiceImpl);
            return delegate;
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskRequest taskRequest;
    private TaskResponse taskResponse;

    @BeforeEach
    void setup() {
        taskRequest = new TaskRequest();
        taskRequest.setTitle("Tarefa 1");
        taskRequest.setDescription("Descrição da tarefa");
        taskRequest.setStatus(TaskRequest.StatusEnum.valueOf("TO_DO"));
        taskRequest.setAccountId(1);

        taskResponse = new TaskResponse();
        taskResponse.setId(VALID_ID);
        taskResponse.setTitle("Tarefa 1");
        taskResponse.setDescription("Descrição da tarefa");
        taskResponse.setStatus(TaskResponse.StatusEnum.valueOf("TO_DO"));
        taskResponse.setAccountId(1);
    }

    @Test
    void shouldCreateNewTask() throws Exception {
        when(taskServiceImpl.createNewTask(any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_PATH + "/" + VALID_ID))
                .andExpect(jsonPath("$.title").value("Tarefa 1"));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        when(taskServiceImpl.getTaskById(VALID_ID)).thenReturn(taskResponse);

        mockMvc.perform(get(BASE_PATH + "/" + VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID));
    }

    @Test
    void shouldReturn404WithMessageWhenTaskNotFound() throws Exception {
        when(taskServiceImpl.getTaskById(INVALID_ID))
                .thenThrow(new EntityNotFoundException("Task not found with id " + INVALID_ID));

        mockMvc.perform(get(BASE_PATH + "/" + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id " + INVALID_ID));
    }

    @Test
    void shouldReturnTasksByAssignedUser() throws Exception {
        when(taskServiceImpl.findAllByAccountId(1)).thenReturn(List.of(taskResponse));

        mockMvc.perform(get(BASE_PATH).param("assignedTo", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId").value(1));
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksForUser() throws Exception {
        when(taskServiceImpl.findAllByAccountId(2)).thenReturn(List.of());

        mockMvc.perform(get(BASE_PATH).param("assignedTo", "2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400WhenMissingAssignedToParam() throws Exception {
        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        when(taskServiceImpl.updateTaskById(Mockito.eq(VALID_ID), any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(put(BASE_PATH + "/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID));
    }

    @Test
    void shouldReturn404WhenUpdateNonExistingTask() throws Exception {
        when(taskServiceImpl.updateTaskById(Mockito.eq(INVALID_ID), any(TaskRequest.class)))
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
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(taskServiceImpl).deleteTaskByID(INVALID_ID);

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
