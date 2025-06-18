package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.factory.TokenFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityTokenUseCaseImplTest {

    @Mock
    private RepositoryTokenPort repositoryTokenPort;

    @InjectMocks
    private SecurityTokenUseCaseImpl securityTokenUseCase;

    @Test
    void shouldReturnTrueWhenTokenIsRevoked() {
        var token = TokenFactory.tokenFactory();
        var tokenValue = token.getToken();
        token.setRevoked(true);

        when(repositoryTokenPort.findByToken(tokenValue)).thenReturn(Optional.of(token));

        boolean result = securityTokenUseCase.tokenIsValid(tokenValue);

        assertTrue(result);
        verify(repositoryTokenPort).findByToken(tokenValue);
    }

    @Test
    void shouldReturnFalseWhenTokenIsNotRevoked() {
        var token = TokenFactory.tokenFactory();
        var tokenValue = token.getToken();
        token.setRevoked(false);

        when(repositoryTokenPort.findByToken(tokenValue)).thenReturn(Optional.of(token));

        boolean result = securityTokenUseCase.tokenIsValid(tokenValue);

        assertFalse(result);
        verify(repositoryTokenPort).findByToken(tokenValue);
    }

    @Test
    void shouldThrowExceptionWhenTokenNotFound() {
        String tokenValue = "missing-token";
        when(repositoryTokenPort.findByToken(tokenValue)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> securityTokenUseCase.tokenIsValid(tokenValue));

        assertEquals("Token not found with token " + tokenValue, exception.getMessage());
        verify(repositoryTokenPort).findByToken(tokenValue);
    }

    @Test
    void shouldReturnTrueForIgnoredPath() {
        assertTrue(securityTokenUseCase.pathIsValid("/swagger-ui/index.html"));
        assertTrue(securityTokenUseCase.pathIsValid("/h2-console/login.do"));
        assertTrue(securityTokenUseCase.pathIsValid("/v3/api-docs"));
        assertTrue(securityTokenUseCase.pathIsValid("/webjars/springfox-swagger-ui/"));
        assertTrue(securityTokenUseCase.pathIsValid("/auth/login"));
    }

    @Test
    void shouldReturnFalseForProtectedPath() {
        assertFalse(securityTokenUseCase.pathIsValid("/account/1"));
        assertFalse(securityTokenUseCase.pathIsValid("/project"));
    }

}
