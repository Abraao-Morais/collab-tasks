package com.example.collabtaskapi.domain;

import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;

import java.time.LocalDate;
import java.util.Objects;

public class Task {

    private Integer id;
    private String title;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private String description;
    private Account account;

    public Task() {
    }

    public Task(Integer id, String title, Status status, Priority priority, LocalDate dueDate, String description, Account account) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.description = description;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
