package com.example.reward.restaurant.producer;

import com.example.reward.shared.config.RabbitMQConfig;
import com.example.reward.shared.event.TransactionCreatedEvent;
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
public class TransactionProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TransactionProducer transactionProducer;

    @Test
    public void testPublishTransactionCreated() {
        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .transactionDate(LocalDateTime.now())
                .build();

        doNothing().when(rabbitTemplate).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.TRANSACTION_ROUTING_KEY),
                eq(event)
        );

        transactionProducer.publishTransactionCreated(event);

        verify(rabbitTemplate, times(1)).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.TRANSACTION_ROUTING_KEY),
                eq(event)
        );
    }
}
