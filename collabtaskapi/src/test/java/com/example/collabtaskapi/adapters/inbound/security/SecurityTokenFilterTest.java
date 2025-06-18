package com.example.collabtaskapi.adapters.inbound.security;

import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityTokenFilterTest {


    @Mock
    private SecurityTokenUseCase securityTokenUseCase;

    @InjectMocks
    private SecurityTokenFilter securityTokenFilter;

    private FilterChain filterChain;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        filterChain = mock(FilterChain.class);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void shouldAllowFilterIfPathIsValid() throws ServletException, IOException {
        request.setServletPath("/auth/login");

        when(securityTokenUseCase.pathIsValid("/auth/login")).thenReturn(true);

        securityTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldBlockRequestIfTokenIsRevoked() throws ServletException, IOException {
        request.setServletPath("/api/resource");
        request.addHeader("Authorization", "Bearer revokedToken");

        when(securityTokenUseCase.pathIsValid("/api/resource")).thenReturn(false);
        when(securityTokenUseCase.tokenIsValid("revokedToken")).thenReturn(true);

        securityTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(request, response);
        assertEquals(401, response.getStatus());
    }

    @Test
    void shouldAllowRequestIfTokenIsValid() throws ServletException, IOException {
        request.setServletPath("/api/resource");
        request.addHeader("Authorization", "Bearer validToken");

        when(securityTokenUseCase.pathIsValid("/api/resource")).thenReturn(false);
        when(securityTokenUseCase.tokenIsValid("validToken")).thenReturn(false);

        securityTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldHandleMissingTokenGracefully() throws ServletException, IOException {
        request.setServletPath("/api/resource");

        when(securityTokenUseCase.pathIsValid("/api/resource")).thenReturn(false);
        when(securityTokenUseCase.tokenIsValid(null)).thenReturn(false);

        securityTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertEquals(200, response.getStatus());
    }

}
