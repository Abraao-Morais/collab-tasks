package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.controllers.TaskApiDelegate;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class TaskApiDelegateImpl implements TaskApiDelegate {

    private static final Logger log = LoggerFactory.getLogger(TaskApiDelegateImpl.class);

    private final RestTaskUseCase restTaskUseCase;

    public TaskApiDelegateImpl(RestTaskUseCase restTaskUseCase) {
        this.restTaskUseCase = restTaskUseCase;
    }

    @Override
    public ResponseEntity<TaskResponse> createNewTask(TaskRequest taskRequest) {
        log.info("Requisição para criar nova tarefa: {}", taskRequest);
        TaskResponse createdTask = restTaskUseCase.createNewTask(taskRequest);

        if (createdTask == null) {
            log.warn("Falha ao criar tarefa: dados inválidos ou operação não concluída.");
            return ResponseEntity.badRequest().build();
        }

        log.info("Tarefa criada com ID: {}", createdTask.getId());
        return ResponseEntity
                .created(URI.create("/task/" + createdTask.getId()))
                .body(createdTask);
    }

    @Override
    public ResponseEntity<Void> deleteTaskById(Integer id) {
        log.info("Requisição para deletar tarefa com ID: {}", id);
        restTaskUseCase.deleteTaskById(id);
        log.info("Tarefa com ID {} deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> getTaskById(Integer id) {
        log.info("Requisição para obter tarefa com ID: {}", id);
        TaskResponse taskResponse = restTaskUseCase.getTaskById(id);
        log.debug("Tarefa encontrada: {}", taskResponse);
        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        log.info("Requisição para listar todas as tarefas");
        List<TaskResponse> tasks = restTaskUseCase.listAllTasks();
        log.info("Total de tarefas encontradas: {}", tasks);
        if (tasks.isEmpty()) {
            log.info("Nenhuma tarefa encontrada");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getTasksByAssignedTo(Integer assignedTo, String status,
                                                                   String priority, Date dueBefore) {
        log.info("Requisição para buscar tarefas com filtros - assignedTo: {}, status: {}, priority: {}, dueBefore: {}",
                assignedTo, status, priority, dueBefore);

        LocalDate dueBeforeDate = null;
        if (nonNull(dueBefore)) {
            dueBeforeDate = dueBefore.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        Status statusEnum = null;
        if (nonNull(status) && !status.isBlank()) {
            try {
                statusEnum = Status.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Valor inválido para status: {}", status);
                return ResponseEntity.badRequest().body(null);
            }
        }

        Priority priorityEnum = null;
        if (nonNull(priority) && !priority.isBlank()) {
            try {
                priorityEnum = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Valor inválido para prioridade: {}", priority);
                return ResponseEntity.badRequest().body(null);
            }
        }

        List<TaskResponse> responseList = restTaskUseCase.listTasksByFilters(assignedTo, statusEnum, priorityEnum, dueBeforeDate);
        log.info("Tarefas encontradas com os filtros fornecidos: {}", responseList);

        if (responseList.isEmpty()) {
            log.info("Nenhuma tarefa encontrada com os filtros fornecidos.");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTaskById(Integer id, TaskRequest taskRequest) {
        log.info("Requisição para atualizar tarefa com ID: {}", id);
        TaskResponse taskResponse = restTaskUseCase.updateTaskById(id, taskRequest);
        log.info("Tarefa com ID {} atualizada com sucesso", id);
        return ResponseEntity.ok(taskResponse);
    }
}

