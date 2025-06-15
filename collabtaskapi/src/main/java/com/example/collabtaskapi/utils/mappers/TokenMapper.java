package com.example.collabtaskapi.utils.mappers;

import com.example.collabtaskapi.adapters.outbound.persistence.entities.JpaTokenEntity;
import com.example.collabtaskapi.domain.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    Token jpaTokenEntityToToken(JpaTokenEntity jpaTokenEntity);
    JpaTokenEntity tokenToJpaTokenEntity(Token token);

}
