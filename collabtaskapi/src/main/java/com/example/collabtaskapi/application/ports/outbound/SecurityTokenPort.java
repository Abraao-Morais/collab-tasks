package com.example.collabtaskapi.application.ports.outbound;

import com.example.collabtaskapi.domain.enums.RoleType;

public interface SecurityTokenPort {

    String generateToken(String userName, RoleType role);

}
