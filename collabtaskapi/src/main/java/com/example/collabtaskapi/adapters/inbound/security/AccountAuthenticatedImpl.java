package com.example.collabtaskapi.adapters.inbound.security;

import com.example.collabtaskapi.adapters.inbound.entities.SecurityAccountEntity;
import com.example.collabtaskapi.application.ports.outbound.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountAuthenticatedImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountAuthenticatedImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByName(username)
                .map(SecurityAccountEntity::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
