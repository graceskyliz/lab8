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
public class RewardProcessedEvent implements Serializable {
    private BigDecimal amount;
    private String cardNumber;
    private String restaurantCode;
    private BigDecimal points;
    private BigDecimal cashback;
    private LocalDateTime processedDate;
}
