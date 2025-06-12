package com.example.collabtaskapi.adapters.inbound.restservices.outbound.repository;

import com.example.collabtaskapi.adapters.outbound.entities.JpaTaskEntity;
import com.example.collabtaskapi.adapters.outbound.repositories.JpaTaskRepository;
import com.example.collabtaskapi.adapters.outbound.repositories.TaskRepositoryImpl;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.factory.TaskFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryImplTest {

    @Mock
    private JpaTaskRepository jpaTaskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private TaskRepositoryImpl taskRepositoryImpl;


    @Test
    public void shouldReturnTaskWhenIdExists() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();
        Task task = TaskFactory.taskFactory();

        when(jpaTaskRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        Optional<Task> result = taskRepositoryImpl.findById(entity.getId());

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    public void shouldReturnListOfTasksByAccountId() {
        JpaTaskEntity entity1 = TaskFactory.jpaTaskEntityFactory();
        JpaTaskEntity entity2 = TaskFactory.jpaTaskEntityFactory();

        Task task1 = TaskFactory.taskFactory();
        Task task2 = TaskFactory.taskFactory();

        when(jpaTaskRepository.findAllByAccountId(1)).thenReturn(Arrays.asList(entity1, entity2));
        when(taskMapper.jpaTaskEntityToTask(entity1)).thenReturn(task1);
        when(taskMapper.jpaTaskEntityToTask(entity2)).thenReturn(task2);

        List<Task> result = taskRepositoryImpl.findAllByAccountId(1);

        assertEquals(2, result.size());
        assertEquals(task1, result.get(0));
        assertEquals(task2, result.get(1));
    }

    @Test
    public void shouldSaveAndReturnMappedTask() {
        Task task = TaskFactory.taskFactory();
        Account account = task.getAccount();
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();

        when(accountMapper.accountToJpaAccountEntity(account)).thenReturn(entity.getAccount());
        when(jpaTaskRepository.save(any(JpaTaskEntity.class))).thenReturn(entity);
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        Task result = taskRepositoryImpl.save(task);

        assertNotNull(result);
        assertEquals(task.getTitle(), result.getTitle());
    }

    @Test
    public void shouldDeleteTaskWhenExists() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();

        when(jpaTaskRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        taskRepositoryImpl.delete(entity.getId());

        verify(jpaTaskRepository, times(1)).delete(entity);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteTaskNotFound() {
        Integer id = 999;

        when(jpaTaskRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            taskRepositoryImpl.delete(id);
        });

        assertEquals("Task not found with id " + id, thrown.getMessage());
    }
}
