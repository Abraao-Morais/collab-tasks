package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.dtos.AuthRequest;

public interface RestAuthUseCase {

    String login(AuthRequest authRequest);
    void logout(String jwtToken);

}
