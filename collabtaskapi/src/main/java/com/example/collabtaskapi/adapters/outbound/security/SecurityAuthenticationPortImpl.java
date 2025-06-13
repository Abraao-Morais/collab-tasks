package com.example.collabtaskapi.adapters.outbound.security;

import com.example.collabtaskapi.application.ports.outbound.SecurityAuthenticationPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityAuthenticationPortImpl implements SecurityAuthenticationPort {

    private final AuthenticationManager authenticationManager;

    public SecurityAuthenticationPortImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
    }
}
