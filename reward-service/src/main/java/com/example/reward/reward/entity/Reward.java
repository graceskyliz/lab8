package com.example.reward.reward.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rewards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String restaurantCode;

    @Column(nullable = false)
    private BigDecimal points;

    @Column(nullable = false)
    private BigDecimal cashback;

    @Column(nullable = false)
    private LocalDateTime processedDate;
}
