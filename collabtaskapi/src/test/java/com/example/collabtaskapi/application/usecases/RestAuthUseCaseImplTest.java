package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.dtos.AuthRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestAuthUseCaseImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SecurityTokenPort securityTokenPort;

    @InjectMocks
    private RestAuthUseCaseImpl restAuthUseCase;

    @Test
    void shouldAuthenticateAndReturnToken() {
        Authentication inputAuthentication = mock(Authentication.class);
        Authentication authenticated = mock(Authentication.class);
        String expectedToken = "mocked.jwt.token";

        when(authenticationManager.authenticate(inputAuthentication)).thenReturn(authenticated);
        when(securityTokenPort.generateToken(String.valueOf(authenticated))).thenReturn(expectedToken);

        String actualToken = restAuthUseCase.login((AuthRequest) inputAuthentication);

        assertEquals(expectedToken, actualToken);
        verify(authenticationManager).authenticate(inputAuthentication);
        verify(securityTokenPort).generateToken(String.valueOf(authenticated));
    }
}
