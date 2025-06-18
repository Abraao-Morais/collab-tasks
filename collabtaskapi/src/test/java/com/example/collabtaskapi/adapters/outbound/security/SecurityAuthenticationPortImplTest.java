package com.example.collabtaskapi.adapters.outbound.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityAuthenticationPortImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private SecurityAuthenticationPortImpl securityAuthenticationPort;

    @Test
    void shouldAuthenticateSuccessfully() {
        String username = "user";
        String password = "pass";
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        assertDoesNotThrow(() -> securityAuthenticationPort.authenticate(username, password));

        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

}
