package com.example.reward.restaurant.controller;

import com.example.reward.restaurant.entity.Transaction;
import com.example.reward.restaurant.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterTransaction() throws Exception {
        Transaction transaction = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(120.50))
                .cardNumber("1234-5678-9012-3456")
                .restaurantCode("REST-99")
                .transactionDate(LocalDateTime.of(2026, 5, 30, 19, 0, 0))
                .build();

        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(120.50))
                .andExpect(jsonPath("$.cardNumber").value("1234-5678-9012-3456"))
                .andExpect(jsonPath("$.restaurantCode").value("REST-99"));

        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }
}
