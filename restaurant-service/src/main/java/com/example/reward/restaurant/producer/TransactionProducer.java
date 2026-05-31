package com.example.reward.restaurant.producer;

import com.example.reward.shared.config.RabbitMQConfig;
import com.example.reward.shared.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishTransactionCreated(TransactionCreatedEvent event) {
        log.info("Publishing TransactionCreatedEvent to RabbitMQ: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.TRANSACTION_ROUTING_KEY,
                event
        );
    }
}
