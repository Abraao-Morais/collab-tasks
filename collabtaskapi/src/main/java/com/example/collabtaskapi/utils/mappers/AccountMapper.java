package com.example.collabtaskapi.utils.mappers;

import com.example.collabtaskapi.adapters.outbound.entities.JpaAccountEntity;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.dtos.AccountRequest;
import com.example.collabtaskapi.dtos.AccountResponse;
import org.mapstruct.Mapper;

import java.net.URI;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account jpaAccountEntityToAccount(JpaAccountEntity jpaAccountEntity);
    Account accountRequestToAccount(AccountRequest accountRequest);
    AccountResponse accountToAccountResponse(Account account);
    JpaAccountEntity accountToJpaAccountEntity(Account account);
    default String map(URI uri) {
        return uri != null ? uri.toString() : null;
    }

    default URI map(String uri) {
        try {
            return (uri != null && !uri.isEmpty()) ? new URI(uri) : null;
        } catch (Exception e) {
            throw new RuntimeException("Invalid URI: " + uri, e);
        }
    }
}
