package com.example.reward.restaurant.service;

import com.example.reward.restaurant.entity.Transaction;
import com.example.reward.restaurant.producer.TransactionProducer;
import com.example.reward.restaurant.repository.TransactionRepository;
import com.example.reward.shared.event.TransactionCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionProducer transactionProducer;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testCreateTransaction() {
        Transaction input = Transaction.builder()
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .build();

        Transaction saved = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .transactionDate(LocalDateTime.now())
                .build();

        when(transactionRepository.save(any(Transaction.class))).thenReturn(saved);
        doNothing().when(transactionProducer).publishTransactionCreated(any(TransactionCreatedEvent.class));

        Transaction result = transactionService.createTransaction(input);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        assertNotNull(result.getTransactionDate());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transactionProducer, times(1)).publishTransactionCreated(any(TransactionCreatedEvent.class));
    }
}
