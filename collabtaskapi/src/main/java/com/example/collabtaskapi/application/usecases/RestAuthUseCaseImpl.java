package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

public class RestAuthUseCaseImpl implements RestAuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final SecurityTokenPort securityTokenPort;

    public RestAuthUseCaseImpl(AuthenticationManager authenticationManager, SecurityTokenPort securityTokenPort) {
        this.authenticationManager = authenticationManager;
        this.securityTokenPort = securityTokenPort;
    }


    public String login(Authentication authentication){
        Authentication authenticated = authenticationManager.authenticate(authentication);
        return securityTokenPort.generateToken(authenticated);
    }

    public void logout(){

    }
}
