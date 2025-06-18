package com.example.collabtaskapi.factory;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.domain.enums.RoleType;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;

import java.net.URI;
import java.util.List;

public class AccountFactory {

    private static final Integer ID = 1;
    private static final String NAME = "João Silva";
    private static final String EMAIL = "joao.silva@example.com";
    private static final String PASSWORD = "senha@123";
    private static final URI PROFILE_PHOTO_URI = URI.create("https://thispersondoesnotexist.com");
    private static final RoleType ROLE = RoleType.ADMIN;
    private static final boolean IS_ACTIVE = true;

    public static Account accountFactory(){
        Account entity = new Account();
        entity.setId(ID);
        entity.setName(NAME);
        entity.setEmail(EMAIL);
        entity.setPassword(PASSWORD);
        entity.setProfilePhotoUrl(String.valueOf(PROFILE_PHOTO_URI));
        entity.setRole(ROLE);
        entity.setActive(IS_ACTIVE);
        return entity;
    }

    public static JpaAccountEntity jpaAccountEntityFactory(){
        JpaAccountEntity entity = new JpaAccountEntity();
        entity.setId(ID);
        entity.setName(NAME);
        entity.setEmail(EMAIL);
        entity.setPassword(PASSWORD);
        entity.setProfilePhotoUrl(String.valueOf(PROFILE_PHOTO_URI));
        entity.setRole(ROLE);
        entity.setActive(IS_ACTIVE);
        entity.setTokens(List.of(TokenFactory.jpaTokenEntityFactory()));
        return entity;
    }

    public static AccountResponse accountResponseFactory(){
        AccountResponse entity = new AccountResponse();
        entity.setId(ID);
        entity.setName(NAME);
        entity.setEmail(EMAIL);
        entity.setProfilePhotoUrl(PROFILE_PHOTO_URI);
        entity.setActive(IS_ACTIVE);
        return entity;
    }

    public static AccountRequest accountRequestFactory(){
        AccountRequest entity = new AccountRequest();
        entity.setName(NAME);
        entity.setEmail(EMAIL);
        entity.setPassword(PASSWORD);
        entity.setProfilePhotoUrl(PROFILE_PHOTO_URI);
        return entity;
    }
}

