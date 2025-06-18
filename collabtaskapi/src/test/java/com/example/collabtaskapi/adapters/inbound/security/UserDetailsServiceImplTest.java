package com.example.collabtaskapi.adapters.inbound.security;

import com.example.collabtaskapi.adapters.inbound.security.entities.UserDetailsImpl;
import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.factory.AccountFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private SecurityAccountUseCase securityAccountUseCase;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private static final String NAME = "João Silva";
    private static final String INVALID_NAME = "unknown";
    private static final String PASSWORD = "senha@123";

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        Account account = AccountFactory.accountFactory();

        when(securityAccountUseCase.getAccountByName(NAME)).thenReturn(account);

        UserDetails userDetails = userDetailsService.loadUserByUsername(NAME);

        assertNotNull(userDetails);
        assertEquals(NAME, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
        assertTrue(userDetails instanceof UserDetailsImpl);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(securityAccountUseCase.getAccountByName(INVALID_NAME))
                .thenThrow(new EntityNotFoundException("Account not found with name " + INVALID_NAME));

        assertThrows(EntityNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(INVALID_NAME);
        });
    }

}
