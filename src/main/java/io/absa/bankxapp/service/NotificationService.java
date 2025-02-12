package io.absa.bankxapp.service;

import io.absa.bankxapp.model.Customer;
import io.absa.bankxapp.model.Notification;
import io.absa.bankxapp.model.NotificationType;
import io.absa.bankxapp.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Async
    @Transactional
    public void sendNotification(Customer customer, NotificationType notificationType, String message) {
        String email = customer.getEmail();
        logger.info("Sending {} notification to {} ({}) - Message: {}",
                notificationType, customer.getName(), email, message);

        // Store the notification in the database
        Notification notification = new Notification(notificationType, message, email);
        notificationRepository.save(notification);

        // TODO: Integrate with Email/SMS API (e.g., Twilio, SendGrid)

    }
}