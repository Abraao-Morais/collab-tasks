package com.example.collabtaskapi.adapters.inbound.restservice;

import com.example.collabtaskapi.application.service.AccountServiceImpl;
import com.example.collabtaskapi.controller.AccountApiController;
import com.example.collabtaskapi.controller.AccountApiDelegate;
import com.example.collabtaskapi.dto.AccountRequest;
import com.example.collabtaskapi.dto.AccountResponse;
import com.example.collabtaskapi.factory.AccountFactory;
import com.example.collabtaskapi.infrastructure.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;



import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountApiController.class)
@Import(AccountApiDelegateImplTest.TestConfig.class)
public class AccountApiDelegateImplTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public AccountServiceImpl accountServiceImpl() {
            return Mockito.mock(AccountServiceImpl.class);
        }

        @Bean
        public AccountApiDelegate accountApiDelegate(AccountServiceImpl accountServiceImpl) {
            AccountApiDelegateImpl delegate = new AccountApiDelegateImpl();
            delegate.setAccountServiceImpl(accountServiceImpl);
            return delegate;
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountRequest accountRequest;
    private AccountResponse accountResponse;

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 999;
    private static final String BASE_PATH = "/account";


    @BeforeEach
    void setup() {
        accountRequest = AccountFactory.accountRequestFactory();
        accountResponse = AccountFactory.accountResponseFactory();
    }

    @Test
    void shouldCreateNewAccount() throws Exception {
        when(accountServiceImpl.createNewAccount(any(AccountRequest.class))).thenReturn(accountResponse);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", BASE_PATH + "/" + VALID_ID))
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    void shouldReturnAccountById() throws Exception {
        when(accountServiceImpl.getAccountById(VALID_ID)).thenReturn(accountResponse);

        mockMvc.perform(get(BASE_PATH + "/" + VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID));
    }

    @Test
    void shouldReturn404WhenAccountNotFound() throws Exception {
        when(accountServiceImpl.getAccountById(INVALID_ID))
                .thenThrow(new EntityNotFoundException("Account not found with id " + INVALID_ID));

        mockMvc.perform(get(BASE_PATH + "/" + INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account not found with id " + INVALID_ID));
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        when(accountServiceImpl.listAllAccounts()).thenReturn(List.of(accountResponse));

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(VALID_ID));
    }

    @Test
    void shouldReturnNoContentWhenNoAccounts() throws Exception {
        when(accountServiceImpl.listAllAccounts()).thenReturn(List.of());

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        when(accountServiceImpl.updateAccountByID(Mockito.eq(VALID_ID), any(AccountRequest.class)))
                .thenReturn(accountResponse);

        mockMvc.perform(put(BASE_PATH + "/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(VALID_ID));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentAccount() throws Exception {
        when(accountServiceImpl.updateAccountByID(Mockito.eq(INVALID_ID), any(AccountRequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put(BASE_PATH + "/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/" + VALID_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentAccount() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(accountServiceImpl).deleteAccountByID(INVALID_ID);

        mockMvc.perform(delete(BASE_PATH + "/" + INVALID_ID))
                .andExpect(status().isNotFound());
    }
}
