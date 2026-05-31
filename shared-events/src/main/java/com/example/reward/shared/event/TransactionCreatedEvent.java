package com.example.reward.shared.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreatedEvent implements Serializable {
    private BigDecimal amount;
    private String cardNumber;
    private String restaurantCode;
    private LocalDateTime transactionDate;
}
