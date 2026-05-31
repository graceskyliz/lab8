package com.example.reward.restaurant.service;

import com.example.reward.restaurant.entity.Transaction;
import com.example.reward.restaurant.producer.TransactionProducer;
import com.example.reward.restaurant.repository.TransactionRepository;
import com.example.reward.shared.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionProducer transactionProducer;

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDateTime.now());
        }
        
        log.info("Persisting transaction in DB for restaurant: {}", transaction.getRestaurantCode());
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Map and publish event
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
                .amount(savedTransaction.getAmount())
                .cardNumber(savedTransaction.getCardNumber())
                .restaurantCode(savedTransaction.getRestaurantCode())
                .transactionDate(savedTransaction.getTransactionDate())
                .build();

        transactionProducer.publishTransactionCreated(event);

        return savedTransaction;
    }
}
