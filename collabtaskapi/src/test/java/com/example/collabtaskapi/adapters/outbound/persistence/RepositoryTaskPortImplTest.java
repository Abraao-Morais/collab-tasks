package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaTaskRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.factory.TaskFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositoryTaskPortImplTest {

    @Mock
    private JpaTaskRepository jpaTaskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private RepositoryTaskPortImpl repositoryTaskPort;

    @Test
    public void shouldReturnTaskWhenIdExists() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();
        Task task = TaskFactory.taskFactory();

        when(jpaTaskRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        Optional<Task> result = repositoryTaskPort.findById(entity.getId());

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenFindByIdNotExists() {
        Integer id = 999;

        when(jpaTaskRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Task> result = repositoryTaskPort.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnTasksByAccountId() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();
        Task task = TaskFactory.taskFactory();

        Integer accountId = entity.getAccount().getId();

        when(jpaTaskRepository.findAllByAccountId(accountId)).thenReturn(List.of(entity));
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        List<Task> result = repositoryTaskPort.findAllByAccountId(accountId);

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenFindAllByAccountIdReturnsEmpty() {
        Integer accountId = 999;

        when(jpaTaskRepository.findAllByAccountId(accountId)).thenReturn(List.of());

        List<Task> result = repositoryTaskPort.findAllByAccountId(accountId);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldSaveAndReturnMappedTask() {
        Task task = TaskFactory.taskFactory();
        Account account = task.getAccount();
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();

        when(accountMapper.accountToJpaAccountEntity(account)).thenReturn(entity.getAccount());
        when(jpaTaskRepository.save(any(JpaTaskEntity.class))).thenReturn(entity);
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        Task result = repositoryTaskPort.save(task);

        assertNotNull(result);
        assertEquals(task.getTitle(), result.getTitle());
    }

    @Test
    public void shouldDeleteTaskWhenExists() {
        Task task = TaskFactory.taskFactory();
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();

        when(taskMapper.taskTTaskEntity(task)).thenReturn(entity);

        repositoryTaskPort.delete(task);

        verify(jpaTaskRepository, times(1)).delete(entity);
    }

    @Test
    public void shouldReturnTasksByFilters() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();
        Task task = TaskFactory.taskFactory();

        Integer accountId = 1;
        Status status = Status.TO_DO;
        Priority priority = Priority.HIGH;
        LocalDate dueBefore = LocalDate.of(2025, 6, 25);

        when(jpaTaskRepository.findByFilters(accountId, status, priority, dueBefore)).thenReturn(List.of(entity));
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        List<Task> result = repositoryTaskPort.findByFilters(accountId, status, priority, dueBefore);

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenFindByFiltersReturnsEmpty() {
        Integer accountId = 1;
        Status status = Status.TO_DO;
        Priority priority = Priority.HIGH;
        LocalDate dueBefore = LocalDate.of(2025, 6, 25);

        when(jpaTaskRepository.findByFilters(accountId, status, priority, dueBefore)).thenReturn(List.of());

        List<Task> result = repositoryTaskPort.findByFilters(accountId, status, priority, dueBefore);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnAllTasks() {
        JpaTaskEntity entity = TaskFactory.jpaTaskEntityFactory();
        Task task = TaskFactory.taskFactory();

        when(jpaTaskRepository.findAll()).thenReturn(List.of(entity));
        when(taskMapper.jpaTaskEntityToTask(entity)).thenReturn(task);

        List<Task> result = repositoryTaskPort.findAll();

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenFindAllReturnsEmpty() {
        when(jpaTaskRepository.findAll()).thenReturn(List.of());

        List<Task> result = repositoryTaskPort.findAll();

        assertTrue(result.isEmpty());
    }

}
