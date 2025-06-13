package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import com.example.collabtaskapi.factory.AccountFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private RepositoryAccountPort accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountUseCaseImpl accountService;

    private AccountRequest accountRequest;
    private Account account;
    private AccountResponse accountResponse;

    @BeforeEach
    void setUp() {
        accountRequest = AccountFactory.accountRequestFactory();
        account = AccountFactory.accountFactory();
        accountResponse = AccountFactory.accountResponseFactory();
    }

    @Test
    void shouldListAllAccounts() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        List<AccountResponse> result = accountService.listAllAccounts();

        assertEquals(1, result.size());
        verify(accountRepository).findAll();
    }

    @Test
    void shouldCreateNewAccount() {
        when(accountMapper.accountRequestToAccount(accountRequest)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.createNewAccount(accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);
        verify(accountRepository).save(account);
    }

    @Test
    void shouldReturnAccountById() {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.getAccountById(1);

        assertEquals(accountResponse, result);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundById() {
        when(accountRepository.findById(999)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> accountService.getAccountById(999));

        assertEquals("Account not found with id 999", exception.getMessage());
    }

    @Test
    void shouldDeactivateAccountWhenDeleted() {
        account.setActive(true);

        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        accountService.deleteAccountByID(1);

        assertFalse(account.getIsActive());
        verify(accountRepository).save(account);
    }

    @Test
    void shouldThrowExceptionWhenDeleteAccountNotFound() {
        when(accountRepository.findById(999)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> accountService.deleteAccountByID(999));

        assertEquals("Account not found with id 999", exception.getMessage());
    }

    @Test
    void shouldUpdateAccountWhenExists() {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = accountService.updateAccountByID(1, accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdateAccountNotFound() {
        when(accountRepository.findById(999)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> accountService.updateAccountByID(999, accountRequest));

        assertEquals("Account not found with id 999", exception.getMessage());
    }
}

