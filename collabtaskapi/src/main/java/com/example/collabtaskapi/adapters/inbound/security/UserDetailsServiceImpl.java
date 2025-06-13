package com.example.collabtaskapi.adapters.inbound.security;

import com.example.collabtaskapi.adapters.inbound.security.entities.UserDetailsImpl;
import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.domain.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SecurityAccountUseCase securityAccountUseCase;

    public UserDetailsServiceImpl(SecurityAccountUseCase securityAccountUseCase) {
        this.securityAccountUseCase = securityAccountUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = securityAccountUseCase.getAccountByName(username);
        return new UserDetailsImpl(account);
    }

}
