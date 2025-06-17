package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityAuthenticationPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.domain.enums.RoleType;
import com.example.collabtaskapi.dtos.AuthRequest;
import com.example.collabtaskapi.factory.AccountFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
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
        var account = AccountFactory.accountFactory();
        var expectedToken = "mocked.jwt.token";
        var authRequest = new AuthRequest("username", "password");
        Instant now = Instant.now();
        long expiry = 3600L;

        doNothing().when(securityAuthenticationPort).authenticate(any(), any());
        when(repositoryAccountPort.findByName("username")).thenReturn(Optional.of(account));
        when(securityTokenPort.generateToken(eq(account.getName()), eq(account.getRole()), eq(now), eq(expiry))).thenReturn(expectedToken);
        when(repositoryTokenPort.findAllValidTokenByAccountId(account.getId())).thenReturn(Collections.emptyList());
        doNothing().when(repositoryTokenPort).saveAll(any());
        when(repositoryTokenPort.save(any())).thenReturn(new Token());

        String actualToken = restAuthUseCase.login(authRequest);

        assertEquals(expectedToken, actualToken);
        verify(securityAuthenticationPort).authenticate(any(), any());
        verify(securityTokenPort).generateToken(any(), any(), any(), any());
    }
}
