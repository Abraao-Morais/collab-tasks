package com.example.collabtaskapi.application.ports.outbound;

public interface SecurityTokenPort {

    String generateToken(String userName);

}
