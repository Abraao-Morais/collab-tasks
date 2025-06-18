package com.example.collabtaskapi.adapters.inbound.rest;

import com.example.collabtaskapi.application.ports.inbound.RestAuthUseCase;
import com.example.collabtaskapi.application.ports.inbound.SecurityTokenUseCase;
import com.example.collabtaskapi.application.usecases.SecurityTokenUseCaseImpl;
import com.example.collabtaskapi.controllers.AuthApiController;
import com.example.collabtaskapi.controllers.AuthApiDelegate;
import com.example.collabtaskapi.dtos.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthApiController.class)
@Import(AuthApiDelegateImplTest.TestConfig.class)
public class AuthApiDelegateImplTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public SecurityTokenUseCase securityTokenUseCase(){
            return Mockito.mock(SecurityTokenUseCaseImpl.class);
        }

        @Bean
        public RestAuthUseCase restAuthUseCase() {
            return Mockito.mock(RestAuthUseCase.class);
        }

        @Bean
        public AuthApiDelegate authApiDelegate(RestAuthUseCase restAuthUseCase) {
            return new AuthApiDelegateImpl(restAuthUseCase);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestAuthUseCase restAuthUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthRequest authRequest;
    private static final String TOKEN = "Bearer mocked-token";
    private static final String INVALID_TOKEN = "mocked-token";
    private static final String BASE_PATH = "/auth/";

    @BeforeEach
    void setup() {
        authRequest = new AuthRequest();
        authRequest.setUsername("test");
        authRequest.setPassword("secret");
    }

    @Test
    void shouldLoginAndReturnToken() throws Exception {
        when(restAuthUseCase.login(any(AuthRequest.class))).thenReturn(TOKEN);

        mockMvc.perform(post(BASE_PATH + "login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(TOKEN));
    }

    @Test
    void shouldLogoutWithValidToken() throws Exception {
        var mockRequest = Mockito.mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn(TOKEN);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));

        mockMvc.perform(post(BASE_PATH + "logout")
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void shouldLogoutWithoutValidToken() throws Exception {
        var mockRequest = Mockito.mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn(null);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));

        mockMvc.perform(post(BASE_PATH + "logout")
                        .header("Authorization", INVALID_TOKEN))
                .andExpect(status().isOk());
    }

}
