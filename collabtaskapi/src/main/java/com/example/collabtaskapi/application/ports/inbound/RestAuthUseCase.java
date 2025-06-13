package com.example.collabtaskapi.application.ports.inbound;

import org.springframework.security.core.Authentication;

public interface RestAuthUseCase {

    String authenticate(Authentication authentication);

}
