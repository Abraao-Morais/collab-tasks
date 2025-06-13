package com.example.collabtaskapi.infrastructure.config;

import com.example.collabtaskapi.application.ports.inbound.RestAccountUseCase;
import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTaskPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityEncoderPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
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
    public RestAccountUseCase restAccountUseCase(RepositoryAccountPort accountRepository, SecurityEncoderPort securityEncoderPort, AccountMapper accountMapper) {
        return new RestAccountUseCaseImpl(accountRepository, securityEncoderPort, accountMapper);
    }

    @Bean
    public RestAuthUseCase restAuthUseCase(AuthenticationManager authenticationManager, SecurityTokenPort tokenService) {
        return new RestAuthUseCaseImpl(authenticationManager, tokenService);
    }

    @Bean
    public RestTaskUseCase restTaskUseCase(RepositoryTaskPort taskRepository, TaskMapper taskMapper) {
        return new RestTaskUseCaseImpl(taskRepository, taskMapper);
    }

    @Bean
    public SecurityAccountUseCase securityAccountUseCase(RepositoryAccountPort accountRepository) {
        return new SecurityAccountUseCaseImpl(accountRepository);
    }
}
