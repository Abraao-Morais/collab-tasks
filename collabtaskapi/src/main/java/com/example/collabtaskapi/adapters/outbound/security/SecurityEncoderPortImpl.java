package com.example.collabtaskapi.adapters.outbound.security;

import com.example.collabtaskapi.application.ports.outbound.SecurityEncoderPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityEncoderPortImpl implements SecurityEncoderPort {

    private final PasswordEncoder passwordEncoder;

    public SecurityEncoderPortImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
