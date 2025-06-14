package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.domain.Token;

public interface SecurityTokenUseCase {

    Token findByToken(String token);

}
