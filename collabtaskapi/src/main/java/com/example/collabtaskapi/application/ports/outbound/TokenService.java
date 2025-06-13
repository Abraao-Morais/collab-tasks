package com.example.collabtaskapi.application.ports.outbound;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String generateToken(Authentication authentication);

}
