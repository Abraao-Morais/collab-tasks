package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.factory.AccountFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityAccountUseCaseImplTest {

    @Mock
    private RepositoryAccountPort repositoryAccountPort;

    @InjectMocks
    private SecurityAccountUseCaseImpl securityAccountUseCase;

    @Test
    void shouldReturnAccountWhenNameExists() {
        var expectedAccount = AccountFactory.accountFactory();
        var name = expectedAccount.getName();

        when(repositoryAccountPort.findByName(name)).thenReturn(Optional.of(expectedAccount));

        var result = securityAccountUseCase.getAccountByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(repositoryAccountPort, times(1)).findByName(name);
    }

    @Test
    void shouldThrowExceptionWhenNameNotFound() {
        var name = "unknown";
        when(repositoryAccountPort.findByName(name)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> securityAccountUseCase.getAccountByName(name));

        assertEquals("Account not found with name " + name, exception.getMessage());
        verify(repositoryAccountPort, times(1)).findByName(name);
    }

}
