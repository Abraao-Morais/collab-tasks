package com.example.collabtaskapi.factory;

import com.example.collabtaskapi.adapters.outbound.entities.JpaAccountEntity;
import com.example.collabtaskapi.adapters.outbound.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.dto.TaskRequest;
import com.example.collabtaskapi.dto.TaskResponse;

import static com.example.collabtaskapi.domain.enums.Status.TO_DO;

public class TaskFactory {

    public static Task taskFactory(){
        Account account = new Account();

        account.setId(1);
        account.setName("João Silva");
        account.setEmail("joao.silva@example.com");
        account.setPassword("senha@123");
        account.setProfilePhotoUrl("thispersondoesnotexist.com");
        account.setActive(true);

        Task task = new Task();

        task.setId(1);
        task.setTitle("Titulo da tarefa 1");
        task.setStatus(Status.valueOf(String.valueOf(TO_DO)));
        task.setDescription("Descricao da tarefa 1");
        task.setAccount(account);

        return task;
    }

    public static JpaTaskEntity jpaTaskEntityFactory(){
        JpaAccountEntity account = new JpaAccountEntity();
        account.setId(1);
        account.setName("joao.silva@example.com");
        account.setPassword("senha@123");
        account.setProfilePhotoUrl("thispersondoesnotexist.com");
        account.setActive(true);

        JpaTaskEntity entity = new JpaTaskEntity();
        entity.setId(1);
        entity.setTitle("Titulo da tarefa 1");
        entity.setStatus(Status.TO_DO);
        entity.setDescription("Descricao da tarefa 1");
        entity.setAccount(account);
        return entity;
    }

    public static TaskResponse taskResponseFactory(){
        TaskResponse entity = new TaskResponse();
        entity.setId(1);
        entity.setTitle("Tarefa 1");
        entity.setDescription("Descrição da tarefa");
        entity.setStatus(TaskResponse.StatusEnum.valueOf("TO_DO"));
        entity.setAccountId(1);
        return entity;
    }
    public static TaskRequest taskRequestFactory(){
        TaskRequest entity = new TaskRequest();
        entity.setTitle("Tarefa 1");
        entity.setDescription("Descrição da tarefa");
        entity.setStatus(TaskRequest.StatusEnum.valueOf("TO_DO"));
        entity.setAccountId(1);
        return entity;
    }

}
