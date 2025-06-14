package com.example.collabtaskapi.application.usecases;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityAuthenticationPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.domain.enums.TokenType;
import com.example.collabtaskapi.dtos.AuthRequest;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;

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

    public String login(AuthRequest authRequest){
        securityAuthenticationPort.authenticate(authRequest.getUsername(), authRequest.getPassword());

        Account account = repositoryAccountPort.findByName(authRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with name " + authRequest.getUsername()));

        Token token = new Token();
        token.setToken(securityTokenPort.generateToken(account.getName(),account.getRole()));
        token.setTokenType(TokenType.BEARER);
        token.setAccount(account);
        token.setExpired(false);
        token.setRevoked(false);

        repositoryTokenPort.save(token);

        return token.getToken();
    }

    public void logout(){

    }
}
