package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityAuthenticationPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.domain.enums.RoleType;
import com.example.collabtaskapi.dtos.AuthRequest;
import com.example.collabtaskapi.factory.AccountFactory;
import com.example.collabtaskapi.factory.TokenFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestAuthUseCaseImplTest {

    @Mock
    private RepositoryAccountPort repositoryAccountPort;

    @Mock
    private RepositoryTokenPort repositoryTokenPort;

    @Mock
    private SecurityAuthenticationPort securityAuthenticationPort;

    @Mock
    private SecurityTokenPort securityTokenPort;

    @InjectMocks
    private RestAuthUseCaseImpl restAuthUseCase;

    @Test
    void shouldAuthenticateAndReturnToken() {
        var authRequest = new AuthRequest("john", "password");
        var account = AccountFactory.accountFactory();
        var jwtToken = "token123";

        when(repositoryAccountPort.findByName("john")).thenReturn(Optional.of(account));
        when(securityTokenPort.generateToken(anyString(), any(), any(), anyLong())).thenReturn(jwtToken);
        when(repositoryTokenPort.findAllValidTokenByAccountId(1)).thenReturn(List.of());

        String token = restAuthUseCase.login(authRequest);

        assertEquals("token123", token);
        verify(securityAuthenticationPort).authenticate("john", "password");
        verify(repositoryTokenPort).save(any(Token.class));
    }

    @Test
    void shouldThrowIfAccountNotFoundOnLogin() {
        var authRequest = new AuthRequest("invalid", "pass");
        when(repositoryAccountPort.findByName("invalid")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> restAuthUseCase.login(authRequest));
    }

    @Test
    void shouldLogoutAndRevokeTokens() {
        var account = AccountFactory.accountFactory();

        var token = TokenFactory.tokenFactory();

        when(repositoryTokenPort.findByToken(token.getToken())).thenReturn(Optional.of(token));
        when(repositoryAccountPort.findById(1)).thenReturn(Optional.of(account));
        when(repositoryTokenPort.findAllValidTokenByAccountId(1)).thenReturn(List.of(token));

        restAuthUseCase.logout("token123");

        assertTrue(token.isRevoked());
        verify(repositoryTokenPort).saveAll(List.of(token));
    }

    @Test
    void shouldThrowIfTokenNotFoundOnLogout() {
        when(repositoryTokenPort.findByToken("notfound")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> restAuthUseCase.logout("notfound"));
    }

    @Test
    void shouldThrowIfAccountNotFoundOnLogout() {
        var token = new Token();
        var account = new Account();
        account.setId(999);
        token.setAccount(account);

        when(repositoryTokenPort.findByToken("token123")).thenReturn(Optional.of(token));
        when(repositoryAccountPort.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> restAuthUseCase.logout("token123"));
    }

}
