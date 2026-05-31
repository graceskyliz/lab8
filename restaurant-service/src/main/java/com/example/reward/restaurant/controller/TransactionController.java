package com.example.reward.restaurant.controller;

import com.example.reward.restaurant.entity.Transaction;
import com.example.reward.restaurant.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> registerTransaction(@RequestBody Transaction transaction) {
        Transaction created = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
