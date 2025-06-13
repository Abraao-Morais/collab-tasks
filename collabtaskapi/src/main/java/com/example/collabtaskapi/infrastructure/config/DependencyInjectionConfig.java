package com.example.collabtaskapi.infrastructure.config;

import com.example.collabtaskapi.application.ports.inbound.RestAccountUseCase;
import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.application.ports.outbound.AccountRepository;
import com.example.collabtaskapi.application.ports.outbound.TaskRepository;
import com.example.collabtaskapi.application.ports.outbound.TokenService;
import com.example.collabtaskapi.application.usecases.RestAccountUseCaseImpl;
import com.example.collabtaskapi.application.usecases.RestAuthUseCaseImpl;
import com.example.collabtaskapi.application.usecases.RestTaskUseCaseImpl;
import com.example.collabtaskapi.application.usecases.SecurityAccountUseCaseImpl;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class DependencyInjectionConfig {

    @Bean
    public RestAccountUseCase restAccountUseCase(AccountRepository accountRepository, AccountMapper accountMapper) {
        return new RestAccountUseCaseImpl(accountRepository, accountMapper);
    }

    @Bean
    public RestAuthUseCase restAuthUseCase(AuthenticationManager authenticationManager, TokenService tokenService) {
        return new RestAuthUseCaseImpl(authenticationManager, tokenService);
    }

    @Bean
    public RestTaskUseCase restTaskUseCase(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new RestTaskUseCaseImpl(taskRepository, taskMapper);
    }

    @Bean
    public SecurityAccountUseCase securityAccountUseCase(AccountRepository accountRepository) {
        return new SecurityAccountUseCaseImpl(accountRepository);
    }
}
