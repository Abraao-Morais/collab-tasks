package com.example.collabtaskapi.application.ports.outbound;

public interface SecurityAuthenticationPort {

    void authenticate(String username, String password);

}
