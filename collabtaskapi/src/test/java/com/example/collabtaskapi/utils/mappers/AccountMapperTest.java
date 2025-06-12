package com.example.collabtaskapi.utils.mappers;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import com.example.collabtaskapi.factory.AccountFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class AccountMapperTest {

    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Test
    public void shouldMapJpaAccountEntityToAccount() {
        JpaAccountEntity entity = AccountFactory.jpaAccountEntityFactory();

        Account account = accountMapper.jpaAccountEntityToAccount(entity);

        assertNotNull(account);
        assertEquals(entity.getId(), account.getId());
        assertEquals(entity.getName(), account.getName());
        assertEquals(entity.getEmail(), account.getEmail());
        assertEquals(entity.getProfilePhotoUrl(), account.getProfilePhotoUrl());
        assertEquals(entity.isActive(), account.getIsActive());
    }

    @Test
    public void shouldMapAccountToJpaAccountEntity() {
        Account account = AccountFactory.accountFactory();

        JpaAccountEntity entity = accountMapper.accountToJpaAccountEntity(account);

        assertNotNull(account);
        assertEquals(entity.getId(), account.getId());
        assertEquals(entity.getName(), account.getName());
        assertEquals(entity.getEmail(), account.getEmail());
        assertEquals(entity.getProfilePhotoUrl(), account.getProfilePhotoUrl());
        assertEquals(entity.isActive(), account.getIsActive());
    }

    @Test
    public void shouldMapAccountRequestToAccount() {
        AccountRequest request = AccountFactory.accountRequestFactory();

        Account account = accountMapper.accountRequestToAccount(request);

        assertNotNull(account);
        assertEquals(request.getName(), account.getName());
        assertEquals(request.getEmail(), account.getEmail());
        assertEquals(accountMapper.map(request.getProfilePhotoUrl()), account.getProfilePhotoUrl());
    }

    @Test
    public void shouldMapAccountToAccountResponse() {
        Account account = AccountFactory.accountFactory();

        AccountResponse response = accountMapper.accountToAccountResponse(account);

        assertNotNull(account);
        assertEquals(response.getId(), account.getId());
        assertEquals(response.getName(), account.getName());
        assertEquals(response.getEmail(), account.getEmail());
        assertEquals(accountMapper.map(response.getProfilePhotoUrl()), account.getProfilePhotoUrl());
    }

    @Test
    void shouldReturnNullWhenUriIsNull() {
        assertNull(accountMapper.map((URI) null));
    }

    @Test
    void shouldReturnNullWhenStringIsNull() {
        assertNull(accountMapper.map((String) null));
    }

    @Test
    void shouldReturnNullWhenStringIsEmpty() {
        assertNull(accountMapper.map(""));
    }

    @Test
    void shouldThrowExceptionWhenStringIsInvalidUri() {
        String invalid = "http://[invalid-uri]";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountMapper.map(invalid);
        });

        assertTrue(exception.getMessage().contains("Invalid URI"));
    }
}
