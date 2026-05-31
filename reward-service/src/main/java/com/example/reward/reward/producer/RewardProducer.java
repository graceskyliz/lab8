package com.example.reward.reward.producer;

import com.example.reward.shared.config.RabbitMQConfig;
import com.example.reward.shared.event.RewardProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publishRewardProcessed(RewardProcessedEvent event) {
        log.info("Publishing RewardProcessedEvent to RabbitMQ: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.REWARD_ROUTING_KEY,
                event
        );
    }
}
