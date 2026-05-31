package com.example.reward.notification.consumer;

import com.example.reward.notification.entity.Notification;
import com.example.reward.notification.repository.NotificationRepository;
import com.example.reward.shared.config.RabbitMQConfig;
import com.example.reward.shared.event.RewardProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardConsumer {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = RabbitMQConfig.REWARD_QUEUE)
    public void consumeRewardProcessed(RewardProcessedEvent event) {
        log.info("Received RewardProcessedEvent: {}", event);

        Notification notification = Notification.builder()
                .cardNumber(event.getCardNumber())
                .points(event.getPoints())
                .cashback(event.getCashback())
                .sentDate(LocalDateTime.now())
                .status("SENT")
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        log.info("Saved Notification in DB with ID: {}", savedNotification.getId());

        // Simulate sending email via logs
        log.info("=========================================================================");
        log.info("📧 SIMULATED EMAIL NOTIFICATION SENT!");
        log.info("To: customer_card_{}@restaurant-rewards.com", event.getCardNumber());
        log.info("Subject: ¡Felicidades! Has ganado una recompensa en tu última cena");
        log.info("Body:");
        log.info("  Hola cliente con tarjeta {},", event.getCardNumber());
        log.info("  ¡Tu transacción en el restaurante '{}' por un monto de ${} ha sido procesada con éxito!", event.getRestaurantCode(), event.getAmount());
        log.info("  Has acumulado:");
        log.info("    ✨ {} PUNTOS", event.getPoints());
        log.info("    💵 ${} CASHBACK", event.getCashback());
        log.info("  ¡Gracias por tu preferencia!");
        log.info("=========================================================================");
    }
}
