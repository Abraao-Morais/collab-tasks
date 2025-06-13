package com.example.collabtaskapi.factory;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTaskEntity;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Status;
import com.example.collabtaskapi.dtos.TaskRequest;
import com.example.collabtaskapi.dtos.TaskResponse;

import static com.example.collabtaskapi.domain.enums.Status.TO_DO;

public class TaskFactory {

    private static final Integer ID = 1;
    private static final String TASK_TITLE = "Titulo da tarefa 1";
    private static final String TASK_RESPONSE_TITLE = "Tarefa 1";
    private static final String TASK_DESCRIPTION = "Descricao da tarefa 1";
    private static final String TASK_RESPONSE_DESCRIPTION = "Descrição da tarefa";
    private static final Status TASK_STATUS = TO_DO;
    private static final Integer ACCOUNT_ID = 1;

    public static Task taskFactory(){
        Account account = AccountFactory.accountFactory();

        Task task = new Task();
        task.setId(ID);
        task.setTitle(TASK_TITLE);
        task.setStatus(TASK_STATUS);
        task.setDescription(TASK_DESCRIPTION);
        task.setAccount(account);

        return task;
    }

    public static JpaTaskEntity jpaTaskEntityFactory(){
        JpaAccountEntity account = AccountFactory.jpaAccountEntityFactory();

        JpaTaskEntity entity = new JpaTaskEntity();
        entity.setId(ID);
        entity.setTitle(TASK_TITLE);
        entity.setStatus(TASK_STATUS);
        entity.setDescription(TASK_DESCRIPTION);
        entity.setAccount(account);
        return entity;
    }

    public static TaskResponse taskResponseFactory(){
        TaskResponse entity = new TaskResponse();
        entity.setId(ID);
        entity.setTitle(TASK_RESPONSE_TITLE);
        entity.setDescription(TASK_RESPONSE_DESCRIPTION);
        entity.setStatus(TaskResponse.StatusEnum.valueOf(TASK_STATUS.name()));
        entity.setAccountId(ACCOUNT_ID);
        return entity;
    }

    public static TaskRequest taskRequestFactory(){
        TaskRequest entity = new TaskRequest();
        entity.setTitle(TASK_RESPONSE_TITLE);
        entity.setDescription(TASK_RESPONSE_DESCRIPTION);
        entity.setStatus(TaskRequest.StatusEnum.valueOf(TASK_STATUS.name()));
        entity.setAccountId(ACCOUNT_ID);
        return entity;
    }
}

