package com.example.collabtaskapi.adapters.outbound.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityEncoderPortImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SecurityEncoderPortImpl securityEncoderPort;

    @Test
    void shouldEncodePassword() {
        var rawPassword = "mySecret123";
        var encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = securityEncoderPort.encode(rawPassword);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}
