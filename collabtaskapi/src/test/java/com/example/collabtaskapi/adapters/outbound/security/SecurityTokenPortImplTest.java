package com.example.collabtaskapi.adapters.outbound.security;

import com.example.collabtaskapi.domain.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityTokenPortImplTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private SecurityTokenPortImpl securityTokenPort;

    @Test
    void shouldGenerateToken() {
        String expectedToken = "mocked.jwt.token";
        Instant now = Instant.now();
        long expiry = 3600L;
        String username = "john.doe";
        RoleType role = RoleType.USER;

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(expectedToken);
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        String result = securityTokenPort.generateToken(username, role, now, expiry);

        assertEquals(expectedToken, result);
        verify(jwtEncoder, times(1)).encode(any(JwtEncoderParameters.class));
    }
}
