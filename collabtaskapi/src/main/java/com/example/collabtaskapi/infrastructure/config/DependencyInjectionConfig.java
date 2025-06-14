package com.example.collabtaskapi.infrastructure.config;

import com.example.collabtaskapi.application.ports.inbound.RestAccountUseCase;
import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.inbound.RestTaskUseCase;
import com.example.collabtaskapi.application.ports.inbound.SecurityAccountUseCase;
import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;
import com.example.collabtaskapi.application.ports.outbound.RepositoryAccountPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTaskPort;
import com.example.collabtaskapi.application.ports.outbound.RepositoryTokenPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityAuthenticationPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityEncoderPort;
import com.example.collabtaskapi.application.ports.outbound.SecurityTokenPort;
import com.example.collabtaskapi.application.usecases.RestAccountUseCaseImpl;
import com.example.collabtaskapi.application.usecases.RestAuthUseCaseImpl;
import com.example.collabtaskapi.application.usecases.RestTaskUseCaseImpl;
import com.example.collabtaskapi.application.usecases.SecurityAccountUseCaseImpl;
import com.example.collabtaskapi.application.usecases.SecurityTokenUseCaseImpl;
import com.example.collabtaskapi.utils.mappers.AccountMapper;
import com.example.collabtaskapi.utils.mappers.TaskMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjectionConfig {

    @Bean
    public RestAccountUseCase restAccountUseCase(RepositoryAccountPort accountRepository, SecurityEncoderPort securityEncoderPort, AccountMapper accountMapper) {
        return new RestAccountUseCaseImpl(accountRepository, securityEncoderPort, accountMapper);
    }

    @Bean
    public RestAuthUseCase restAuthUseCase(RepositoryAccountPort accountRepository, RepositoryTokenPort repositoryTokenPort, SecurityAuthenticationPort securityAuthenticationPort, SecurityTokenPort securityTokenPort) {
        return new RestAuthUseCaseImpl(accountRepository, repositoryTokenPort, securityAuthenticationPort, securityTokenPort);
    }

    @Bean
    public RestTaskUseCase restTaskUseCase(RepositoryTaskPort repositoryTaskPort, TaskMapper taskMapper) {
        return new RestTaskUseCaseImpl(repositoryTaskPort, taskMapper);
    }

    @Bean
    public SecurityAccountUseCase securityAccountUseCase(RepositoryAccountPort repositoryAccountPort) {
        return new SecurityAccountUseCaseImpl(repositoryAccountPort);
    }

    @Bean
    public SecurityTokenUseCase securityTokenUseCase(RepositoryTokenPort repositoryTokenPort) {
        return new SecurityTokenUseCaseImpl(repositoryTokenPort);
    }
}
