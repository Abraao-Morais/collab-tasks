package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaAccountEntity;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaAccountRepository;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.factory.AccountFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryImplTest {

    @Mock
    private JpaAccountRepository jpaAccountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountRepositoryImpl accountRepositoryImpl;

    @Test
    void shouldReturnAccountWhenIdExists() {
        JpaAccountEntity entity = AccountFactory.jpaAccountEntityFactory();
        Account account = AccountFactory.accountFactory();

        when(jpaAccountRepository.findById(account.getId())).thenReturn(Optional.of(entity));
        when(accountMapper.jpaAccountEntityToAccount(entity)).thenReturn(account);

        Optional<Account> result = accountRepositoryImpl.findById(account.getId());

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void shouldReturnAccountWhenNameExists() {
        JpaAccountEntity entity = AccountFactory.jpaAccountEntityFactory();
        Account account = AccountFactory.accountFactory();

        when(jpaAccountRepository.findByName(account.getName())).thenReturn(Optional.of(entity));
        when(accountMapper.jpaAccountEntityToAccount(entity)).thenReturn(account);

        Optional<Account> result = accountRepositoryImpl.findByName(account.getName());

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void shouldReturnAllAccounts() {
        JpaAccountEntity entity1 = AccountFactory.jpaAccountEntityFactory();
        JpaAccountEntity entity2 = AccountFactory.jpaAccountEntityFactory();

        Account account1 = AccountFactory.accountFactory();
        Account account2 = AccountFactory.accountFactory();

        when(jpaAccountRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        when(accountMapper.jpaAccountEntityToAccount(entity1)).thenReturn(account1);
        when(accountMapper.jpaAccountEntityToAccount(entity2)).thenReturn(account2);

        List<Account> result = accountRepositoryImpl.findAll();

        assertEquals(2, result.size());
        assertEquals(account1, result.get(0));
        assertEquals(account2, result.get(1));
    }

    @Test
    void shouldSaveAndReturnMappedAccount() {
        Account account = AccountFactory.accountFactory();
        JpaAccountEntity entity = AccountFactory.jpaAccountEntityFactory();

        when(jpaAccountRepository.save(any(JpaAccountEntity.class))).thenReturn(entity);
        when(accountMapper.jpaAccountEntityToAccount(entity)).thenReturn(account);

        Account result = accountRepositoryImpl.save(account);

        assertNotNull(result);
        assertEquals(account.getName(), result.getName());
    }

    @Test
    void shouldDeleteAccountWhenExists() {
        JpaAccountEntity entity = AccountFactory.jpaAccountEntityFactory();

        when(jpaAccountRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        accountRepositoryImpl.delete(entity.getId());

        verify(jpaAccountRepository, times(1)).delete(entity);
    }

    @Test
    void shouldThrowExceptionWhenDeleteAccountNotFound() {
        Integer id = 999;

        when(jpaAccountRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            accountRepositoryImpl.delete(id);
        });

        assertEquals("Account not found with id " + id, thrown.getMessage());
    }
}