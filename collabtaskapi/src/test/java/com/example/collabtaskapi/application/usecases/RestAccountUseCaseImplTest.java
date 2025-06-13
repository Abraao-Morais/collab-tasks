package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.SecurityEncoderPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RestAccountUseCaseImplTest {

    @Mock
    private RepositoryAccountPort repositoryAccountPort;

    @Mock
    private SecurityEncoderPort securityEncoderPort;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private RestAccountUseCaseImpl restAccountUseCase;

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
        when(repositoryAccountPort.findAll()).thenReturn(Arrays.asList(account));
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        List<AccountResponse> result = restAccountUseCase.listAllAccounts();

        assertEquals(1, result.size());
        verify(repositoryAccountPort).findAll();
    }

    @Test
    void shouldCreateNewAccount() {
        when(accountMapper.accountRequestToAccount(accountRequest)).thenReturn(account);
        when(securityEncoderPort.encode(any())).thenReturn(any());
        when(repositoryAccountPort.save(account)).thenReturn(account);
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = restAccountUseCase.createNewAccount(accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);
        verify(repositoryAccountPort).save(account);
    }

    @Test
    void shouldReturnAccountById() {
        when(repositoryAccountPort.findById(1)).thenReturn(Optional.of(account));
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = restAccountUseCase.getAccountById(1);

        assertEquals(accountResponse, result);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundById() {
        when(repositoryAccountPort.findById(999)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restAccountUseCase.getAccountById(999));

        assertEquals("Account not found with id 999", exception.getMessage());
    }

    @Test
    void shouldDeactivateAccountWhenDeleted() {
        account.setActive(true);

        when(repositoryAccountPort.findById(1)).thenReturn(Optional.of(account));
        when(repositoryAccountPort.save(account)).thenReturn(account);

        restAccountUseCase.deleteAccountByID(1);

        assertFalse(account.getIsActive());
        verify(repositoryAccountPort).save(account);
    }

    @Test
    void shouldThrowExceptionWhenDeleteAccountNotFound() {
        when(repositoryAccountPort.findById(999)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restAccountUseCase.deleteAccountByID(999));

        assertEquals("Account not found with id 999", exception.getMessage());
    }

    @Test
    void shouldUpdateAccountWhenExists() {
        when(repositoryAccountPort.findById(1)).thenReturn(Optional.of(account));
        when(repositoryAccountPort.save(account)).thenReturn(account);
        when(accountMapper.accountToAccountResponse(account)).thenReturn(accountResponse);

        AccountResponse result = restAccountUseCase.updateAccountByID(1, accountRequest);

        assertNotNull(result);
        assertEquals(accountResponse, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdateAccountNotFound() {
        when(repositoryAccountPort.findById(999)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> restAccountUseCase.updateAccountByID(999, accountRequest));

        assertEquals("Account not found with id 999", exception.getMessage());
    }
}

