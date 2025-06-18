package com.example.collabtaskapi.factory;

import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.domain.enums.TokenType;


public class TokenFactory {

    private static final Integer ID = 1;
    private static final TokenType TOKEN_TYPE = TokenType.BEARER;
    private static final boolean REVOKED = true;
    private static final Account ACCOUNT = AccountFactory.accountFactory();
    private static final String TOKEN = "tokentoken";


    public static Token tokenFactory(){
        Token entity = new Token();
        entity.setId(ID);
        entity.setTokenType(TOKEN_TYPE);
        entity.setRevoked(REVOKED);
        entity.setAccount(ACCOUNT);
        entity.setToken(String.valueOf(TOKEN));
        return entity;
    }

}
