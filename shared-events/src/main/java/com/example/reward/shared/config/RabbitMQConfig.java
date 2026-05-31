package com.example.reward.shared.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "reward.exchange";
    public static final String TRANSACTION_QUEUE = "transaction.queue";
    public static final String REWARD_QUEUE = "reward.queue";
    public static final String TRANSACTION_ROUTING_KEY = "transaction.created";
    public static final String REWARD_ROUTING_KEY = "reward.processed";

    @Bean
    public TopicExchange rewardExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue transactionQueue() {
        return QueueBuilder.durable(TRANSACTION_QUEUE).build();
    }

    @Bean
    public Queue rewardQueue() {
        return QueueBuilder.durable(REWARD_QUEUE).build();
    }

    @Bean
    public Binding transactionBinding(Queue transactionQueue, TopicExchange rewardExchange) {
        return BindingBuilder.bind(transactionQueue).to(rewardExchange).with(TRANSACTION_ROUTING_KEY);
    }

    @Bean
    public Binding rewardBinding(Queue rewardQueue, TopicExchange rewardExchange) {
        return BindingBuilder.bind(rewardQueue).to(rewardExchange).with(REWARD_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
