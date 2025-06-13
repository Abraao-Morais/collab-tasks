package com.example.collabtaskapi.domain;

import java.util.Objects;

public class Account {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String profilePhotoUrl;
    private boolean isActive;

    public Account() {}

    public Account(Integer id, String name, String email, String password, String profilePhotoUrl, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isActive == account.isActive && Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(email, account.email) && Objects.equals(password, account.password) && Objects.equals(profilePhotoUrl, account.profilePhotoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, profilePhotoUrl, isActive);
    }
}