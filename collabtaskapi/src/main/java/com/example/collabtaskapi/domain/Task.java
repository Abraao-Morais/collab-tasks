package com.example.collabtaskapi.domain;

import com.example.collabtaskapi.domain.enums.Status;

import java.util.Objects;

public class Task {

    private Integer id;
    private String title;
    private Status status;
    private String description;
    private Account account;

    public Task() {
    }

    public Task(Integer id, String title, Status status, String description, Account account) {
        this.id = id;
        this.title = title;
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && status == task.status && Objects.equals(description, task.description) && Objects.equals(account, task.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status, description, account);
    }
}
