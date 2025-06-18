package com.example.collabtaskapi.adapters.inbound.security.entities;

import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.factory.AccountFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDetailsImplTest {

    private Account account;
    private UserDetailsImpl userDetails;
    private static final String NAME = "João Silva";
    private static final String PASSWORD = "senha@123";

    @BeforeEach
    void setUp() {
        account = AccountFactory.accountFactory();
        userDetails = new UserDetailsImpl(account);
    }

    @Test
    void shouldReturnCorrectAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
    }

    @Test
    void shouldReturnCorrectPassword() {
        assertEquals(PASSWORD, userDetails.getPassword());
    }

    @Test
    void shouldReturnCorrectUsername() {
        assertEquals(NAME, userDetails.getUsername());
    }

    @Test
    void shouldAlwaysReturnTrueForAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void shouldAlwaysReturnTrueForAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void shouldAlwaysReturnTrueForCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void shouldReturnEnabledBasedOnAccountStatus() {
        assertTrue(userDetails.isEnabled());

        account.setActive(false);
        UserDetailsImpl inactiveUserDetails = new UserDetailsImpl(account);
        assertFalse(inactiveUserDetails.isEnabled());
    }

}