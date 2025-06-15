package com.example.collabtaskapi.domain;

import com.example.collabtaskapi.domain.enums.TokenType;

public class Token {

    private Integer id;
    private String token;
    private TokenType tokenType;
    private boolean revoked;
    private Account account;

    public Token(){}

    public Token(Integer id, String token, TokenType tokenType, boolean revoked, Account account) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
