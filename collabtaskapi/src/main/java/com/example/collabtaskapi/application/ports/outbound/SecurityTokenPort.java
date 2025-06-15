package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.enums.RoleType;

import java.time.Instant;

public interface SecurityTokenPort {

    String generateToken(String userName, RoleType role, Instant now, long expiry);

}
