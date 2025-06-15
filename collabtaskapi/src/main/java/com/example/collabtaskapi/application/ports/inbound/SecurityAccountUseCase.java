package com.example.collabtaskapi.application.ports.inbound;

import com.example.collabtaskapi.domain.Account;

public interface SecurityAccountUseCase {

    Account getAccountByName(String name);

}
