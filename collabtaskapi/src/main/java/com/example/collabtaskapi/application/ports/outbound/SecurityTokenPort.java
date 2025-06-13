package com.example.collabtaskapi.application.ports.outbound;

import org.springframework.security.core.Authentication;

public interface SecurityTokenPort {

    String generateToken(Authentication authentication);

}
