package com.example.collabtaskapi.application.ports.inbound;

public interface SecurityTokenUseCase {

    boolean tokenIsValid(String token);
    boolean pathIsValid(String path);

}
