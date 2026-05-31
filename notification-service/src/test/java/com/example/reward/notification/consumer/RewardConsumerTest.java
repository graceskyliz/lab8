package com.example.reward.notification.consumer;

import com.example.reward.notification.entity.Notification;
import com.example.reward.notification.repository.NotificationRepository;
import com.example.reward.shared.event.RewardProcessedEvent;
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
public class RewardConsumerTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private RewardConsumer rewardConsumer;

    @Test
    public void testConsumeRewardProcessed() {
        RewardProcessedEvent event = RewardProcessedEvent.builder()
                .amount(BigDecimal.valueOf(100.00))
                .cardNumber("1111-2222-3333-4444")
                .restaurantCode("REST-1")
                .points(BigDecimal.valueOf(1000.00))
                .cashback(BigDecimal.valueOf(5.00))
                .processedDate(LocalDateTime.now())
                .build();

        Notification saved = Notification.builder()
                .id(1L)
                .cardNumber("1111-2222-3333-4444")
                .points(BigDecimal.valueOf(1000.00))
                .cashback(BigDecimal.valueOf(5.00))
                .sentDate(LocalDateTime.now())
                .status("SENT")
                .build();

        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        rewardConsumer.consumeRewardProcessed(event);

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
}
