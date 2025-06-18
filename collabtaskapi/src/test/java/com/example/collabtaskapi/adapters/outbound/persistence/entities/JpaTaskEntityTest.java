package com.example.collabtaskapi.adapters.outbound.persistence.entities;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.RoleType;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.factory.AccountFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JpaTaskEntityTest {

    @Test
    public void shouldCreateEntityFromTask() {
        var account = AccountFactory.accountFactory();
        Task task = new Task(10, "Test Task", Status.TO_DO, Priority.MEDIUM,
                LocalDate.of(2025, 6, 20), "Description", account);

        JpaAccountEntity jpaAccountEntity = new JpaAccountEntity();
        jpaAccountEntity.setId(account.getId());
        jpaAccountEntity.setName(account.getName());
        jpaAccountEntity.setEmail(account.getEmail());
        jpaAccountEntity.setPassword(account.getPassword());
        jpaAccountEntity.setProfilePhotoUrl(account.getProfilePhotoUrl());
        jpaAccountEntity.setRole(account.getRole());
        jpaAccountEntity.setActive(account.isActive());

        JpaTaskEntity entity = new JpaTaskEntity(task, jpaAccountEntity);
        assertEquals(task.getId(), entity.getId());
        assertEquals(task.getTitle(), entity.getTitle());
        assertEquals(task.getStatus(), entity.getStatus());
        assertEquals(task.getPriority(), entity.getPriority());
        assertEquals(task.getDueDate(), entity.getDueDate());
        assertEquals(task.getDescription(), entity.getDescription());
        assertEquals(jpaAccountEntity, entity.getAccount());
    }

    @Test
    public void shouldUseEqualsAndHashCodeBasedOnId() {
        JpaTaskEntity entity1 = new JpaTaskEntity();
        entity1.setId(1);

        JpaTaskEntity entity2 = new JpaTaskEntity();
        entity2.setId(1);

        JpaTaskEntity entity3 = new JpaTaskEntity();
        entity3.setId(2);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());

        assertNotEquals(entity1, entity3);
    }

    @Test
    public void shouldBuildEntityUsingBuilder() {
        JpaTaskEntity entity = JpaTaskEntity.builder()
                .id(5)
                .title("Builder Test")
                .status(Status.DONE)
                .priority(Priority.HIGH)
                .dueDate(LocalDate.of(2025, 7, 1))
                .description("Testing builder")
                .build();

        assertEquals(5, entity.getId());
        assertEquals("Builder Test", entity.getTitle());
        assertEquals(Status.DONE, entity.getStatus());
        assertEquals(Priority.HIGH, entity.getPriority());
        assertEquals(LocalDate.of(2025, 7, 1), entity.getDueDate());
        assertEquals("Testing builder", entity.getDescription());
    }

}
