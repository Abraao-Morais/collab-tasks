package com.example.collabtaskapi.adapters.outbound.persistence;

import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaTokenRepository;
import com.example.collabtaskapi.domain.Token;
import com.example.collabtaskapi.factory.TokenFactory;
import com.example.collabtaskapi.utils.mappers.TokenMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepositoryTokenPortImplTest {

    @Mock
    private JpaTokenRepository jpaTokenRepository;

    @Mock
    private TokenMapper tokenMapper;

    @InjectMocks
    private RepositoryTokenPortImpl repositoryTokenPort;

    private final Integer ACCOUNT_ID = 1;
    private final String TOKEN_STRING = "tokentoken";

    @Test
    void shouldFindAllValidTokensByAccountId() {
        var entity = TokenFactory.jpaTokenEntityFactory();
        var domainToken = TokenFactory.tokenFactory();

        when(jpaTokenRepository.findAllValidTokenByAccountId(ACCOUNT_ID)).thenReturn(List.of(entity));
        when(tokenMapper.jpaTokenEntityToToken(entity)).thenReturn(domainToken);

        List<Token> tokens = repositoryTokenPort.findAllValidTokenByAccountId(ACCOUNT_ID);

        assertEquals(1, tokens.size());
        assertEquals(TOKEN_STRING, tokens.get(0).getToken());
    }

    @Test
    void shouldFindByToken() {
        var entity = TokenFactory.jpaTokenEntityFactory();
        var token = TokenFactory.tokenFactory();

        when(jpaTokenRepository.findByToken(TOKEN_STRING)).thenReturn(Optional.of(entity));
        when(tokenMapper.jpaTokenEntityToToken(entity)).thenReturn(token);

        Optional<Token> result = repositoryTokenPort.findByToken(TOKEN_STRING);

        assertTrue(result.isPresent());
        assertEquals(TOKEN_STRING, result.get().getToken());
        assertEquals(entity.getToken(), result.get().getToken());
        assertEquals(entity.getId(), result.get().getId());
        assertEquals(token.getId(), result.get().getId());
        assertEquals(entity.getTokenType(), result.get().getTokenType());
        assertEquals(token.getTokenType(), result.get().getTokenType());
        assertEquals(entity.getAccount().getId(), result.get().getAccount().getId());
    }

    @Test
    void shouldReturnEmptyWhenTokenNotFound() {
        when(jpaTokenRepository.findByToken(TOKEN_STRING)).thenReturn(Optional.empty());

        Optional<Token> result = repositoryTokenPort.findByToken(TOKEN_STRING);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSaveToken() {
        var entity = TokenFactory.jpaTokenEntityFactory();
        var token = TokenFactory.tokenFactory();

        when(tokenMapper.tokenToJpaTokenEntity(token)).thenReturn(entity);
        when(jpaTokenRepository.save(entity)).thenReturn(entity);
        when(tokenMapper.jpaTokenEntityToToken(entity)).thenReturn(token);

        Token saved = repositoryTokenPort.save(token);

        assertNotNull(saved);
        assertEquals(TOKEN_STRING, saved.getToken());
    }

    @Test
    void shouldSaveAllTokens() {
        var entity = TokenFactory.jpaTokenEntityFactory();
        var token = TokenFactory.tokenFactory();

        when(tokenMapper.tokenToJpaTokenEntity(token)).thenReturn(entity);

        repositoryTokenPort.saveAll(List.of(token));

        verify(jpaTokenRepository, times(1)).saveAll(List.of(entity));
    }

}
