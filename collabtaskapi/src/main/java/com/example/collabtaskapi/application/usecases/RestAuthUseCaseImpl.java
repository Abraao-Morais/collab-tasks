package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityAuthenticationPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.domain.enums.RoleType;
import com.example.collabtaskapi.domain.enums.TokenType;
import com.example.collabtaskapi.dtos.AuthRequest;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;

import java.time.Instant;

public class RestAuthUseCaseImpl implements RestAuthUseCase {

    private final RepositoryAccountPort repositoryAccountPort;
    private final RepositoryTokenPort repositoryTokenPort;
    private final SecurityAuthenticationPort securityAuthenticationPort;
    private final SecurityTokenPort securityTokenPort;

    public RestAuthUseCaseImpl(RepositoryAccountPort repositoryAccountPort, RepositoryTokenPort repositoryTokenPort, SecurityAuthenticationPort securityAuthenticationPort, SecurityTokenPort securityTokenPort) {
        this.repositoryAccountPort = repositoryAccountPort;
        this.repositoryTokenPort = repositoryTokenPort;
        this.securityAuthenticationPort = securityAuthenticationPort;
        this.securityTokenPort = securityTokenPort;
    }

    @Override
    public String login(AuthRequest authRequest){
        securityAuthenticationPort.authenticate(authRequest.getUsername(), authRequest.getPassword());
        var account = repositoryAccountPort.findByName(authRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with name " + authRequest.getUsername()));

        var jwtToken = generateToken(account.getName(), account.getRole());
        revokedAllTokensByUser(account);
        saveToken(jwtToken, account);
        return jwtToken;
    }
    @Override
    public void logout(String jwtToken){
        var token = repositoryTokenPort.findByToken(jwtToken)
                .orElseThrow(() -> new EntityNotFoundException("Token not found with token " + jwtToken));

        var account = repositoryAccountPort.findById(token.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + token.getAccount().getId()));

        revokedAllTokensByUser(account);
    }

    private String generateToken(String username, RoleType role){
        Instant now = Instant.now();
        long expiry = 3600L;

        return securityTokenPort.generateToken(username, role, now, expiry);
    }

    private void revokedAllTokensByUser(Account account){
        var validTokens = repositoryTokenPort.findAllValidTokenByAccountId(account.getId());
        if(validTokens.isEmpty())
            return;
        validTokens.forEach(token -> {
            token.setRevoked(true);
        });
        repositoryTokenPort.saveAll(validTokens);
    }

    private void saveToken(String jwtToken, Account account) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setAccount(account);
        token.setRevoked(false);
        repositoryTokenPort.save(token);
    }
}
