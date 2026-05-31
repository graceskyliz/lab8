package com.example.reward.reward.consumer;

import com.example.reward.reward.entity.Reward;
import com.example.reward.reward.producer.RewardProducer;
import com.example.reward.reward.repository.RewardRepository;
import com.example.reward.shared.event.RewardProcessedEvent;
import com.example.reward.shared.event.TransactionCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionConsumerTest {

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private RewardProducer rewardProducer;

    @InjectMocks
    private TransactionConsumer transactionConsumer;

    @Test
    public void testConsumeTransactionCreated() {
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .transactionDate(LocalDateTime.now())
                .build();

        Reward savedReward = Reward.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .points(BigDecimal.valueOf(1000.00))
                .cashback(BigDecimal.valueOf(5.00))
                .processedDate(LocalDateTime.now())
                .build();

        when(rewardRepository.save(any(Reward.class))).thenReturn(savedReward);
        doNothing().when(rewardProducer).publishRewardProcessed(any(RewardProcessedEvent.class));

        transactionConsumer.consumeTransactionCreated(event);

        verify(rewardRepository, times(1)).save(any(Reward.class));
        verify(rewardProducer, times(1)).publishRewardProcessed(any(RewardProcessedEvent.class));
    }
}
