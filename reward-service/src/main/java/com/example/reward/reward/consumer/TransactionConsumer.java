package com.example.reward.reward.consumer;

import com.example.reward.reward.entity.Reward;
import com.example.reward.reward.producer.RewardProducer;
import com.example.reward.reward.repository.RewardRepository;
import com.example.reward.shared.config.RabbitMQConfig;
import com.example.reward.shared.event.RewardProcessedEvent;
import com.example.reward.shared.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionConsumer {

    private final RewardRepository rewardRepository;
    private final RewardProducer rewardProducer;

    @RabbitListener(queues = RabbitMQConfig.TRANSACTION_QUEUE)
    public void consumeTransactionCreated(TransactionCreatedEvent event) {
        log.info("Received TransactionCreatedEvent: {}", event);

        BigDecimal amount = event.getAmount();
        BigDecimal points = amount.multiply(BigDecimal.valueOf(10));
        BigDecimal cashback = amount.multiply(BigDecimal.valueOf(0.05));

        log.info("Calculated Reward - Points: {}, Cashback: {}", points, cashback);

        Reward reward = Reward.builder()
                .amount(amount)
                .cardNumber(event.getCardNumber())
                .restaurantCode(event.getRestaurantCode())
                .points(points)
                .cashback(cashback)
                .processedDate(LocalDateTime.now())
                .build();

        Reward savedReward = rewardRepository.save(reward);
        log.info("Saved Reward in DB with ID: {}", savedReward.getId());

        // Publish RewardProcessedEvent
        RewardProcessedEvent processedEvent = RewardProcessedEvent.builder()
                .amount(savedReward.getAmount())
                .cardNumber(savedReward.getCardNumber())
                .restaurantCode(savedReward.getRestaurantCode())
                .points(savedReward.getPoints())
                .cashback(savedReward.getCashback())
                .processedDate(savedReward.getProcessedDate())
                .build();

        rewardProducer.publishRewardProcessed(processedEvent);
    }
}
