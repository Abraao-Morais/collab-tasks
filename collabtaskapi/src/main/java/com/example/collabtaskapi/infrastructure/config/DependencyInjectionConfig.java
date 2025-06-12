package com.example.collabtaskapi.infrastructure.config;

import com.example.collabtaskapi.adapters.outbound.persistence.AccountRepositoryImpl;
import com.example.collabtaskapi.adapters.outbound.persistence.TaskRepositoryImpl;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaAccountRepository;
import com.example.collabtaskapi.adapters.outbound.persistence.repositories.JpaTaskRepository;
import com.example.collabtaskapi.adapters.outbound.security.TokenServiceImpl;
import com.example.collabtaskapi.application.ports.inbound.AccountUseCase;
import com.example.collabtaskapi.application.ports.inbound.AuthUseCase;
import com.example.collabtaskapi.application.ports.inbound.TaskUseCase;
import com.example.collabtaskapi.application.ports.outbound.AccountRepository;
import com.example.collabtaskapi.application.ports.outbound.TaskRepository;
import com.example.collabtaskapi.application.ports.outbound.TokenService;
import com.example.collabtaskapi.application.usecases.AccountUseCaseImpl;
import com.example.collabtaskapi.application.usecases.AuthUseCaseImpl;
import com.example.collabtaskapi.application.usecases.TaskUseCaseImpl;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
public class DependencyInjectionConfig {

    @Bean
    public AccountUseCase accountUseCase(AccountRepository accountRepository, AccountMapper accountMapper) {
        return new AccountUseCaseImpl(accountRepository, accountMapper);
    }

    @Bean
    public AuthUseCase authUseCase(AuthenticationManager authenticationManager, TokenService tokenService) {
        return new AuthUseCaseImpl(authenticationManager, tokenService);
    }

    @Bean
    public TaskUseCase taskUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new TaskUseCaseImpl(taskRepository, taskMapper);
    }

    @Bean
    public AccountRepository accountRepository(JpaAccountRepository jpaAccountRepository, AccountMapper accountMapper) {
        return new AccountRepositoryImpl(jpaAccountRepository, accountMapper);
    }

    @Bean
    public TaskRepository taskRepository(JpaTaskRepository jpaTaskRepository, TaskMapper taskMapper, AccountMapper accountMapper) {
        return new TaskRepositoryImpl(jpaTaskRepository, taskMapper, accountMapper);
    }
}
