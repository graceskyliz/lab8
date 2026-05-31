package com.example.reward.reward.producer;

import com.example.reward.shared.config.RabbitMQConfig;
import com.example.reward.shared.event.RewardProcessedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RewardProducer rewardProducer;

    @Test
    public void testPublishRewardProcessed() {
        RewardProcessedEvent event = RewardProcessedEvent.builder()
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .points(BigDecimal.valueOf(1000.00))
                .cashback(BigDecimal.valueOf(5.00))
                .processedDate(LocalDateTime.now())
                .build();

        doNothing().when(rabbitTemplate).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.REWARD_ROUTING_KEY),
                eq(event)
        );

        rewardProducer.publishRewardProcessed(event);

        verify(rabbitTemplate, times(1)).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.REWARD_ROUTING_KEY),
                eq(event)
        );
    }
}
