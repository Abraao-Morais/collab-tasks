package com.example.collabtaskapi.application.ports.inbound;

import org.springframework.security.core.Authentication;

public interface AuthUseCase {

    String authenticate(Authentication authentication);

}
