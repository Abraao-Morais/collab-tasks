package com.example.collabtaskapi.adapters.outbound.security;

import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.domain.enums.RoleType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SecurityTokenPortImpl implements SecurityTokenPort {

    private final JwtEncoder jwtEncoder;

    public SecurityTokenPortImpl(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String userName, RoleType role, Instant now, long expiry) {
        var claims = JwtClaimsSet.builder()
                .issuer("collab")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(userName)
                .claim("role", role.name())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
