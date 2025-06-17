package com.example.collabtaskapi.adapters.inbound.security;

import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
public class SecurityTokenFilter extends OncePerRequestFilter {

    private final SecurityTokenUseCase securityTokenUseCase;

    public SecurityTokenFilter(SecurityTokenUseCase securityTokenUseCase) {
        this.securityTokenUseCase = securityTokenUseCase;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (securityTokenUseCase.tokenIsValid(getTokenFromHeader(request))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token revogado");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (nonNull(header) && header.startsWith("Bearer ")) return header.substring(7);
        return null;
    }

}
