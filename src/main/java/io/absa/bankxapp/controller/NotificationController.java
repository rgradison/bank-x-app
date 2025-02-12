package io.absa.bankxapp.controller;

import io.absa.bankxapp.model.Notification;
import io.absa.bankxapp.repository.NotificationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Get all notifications for a customer
    @GetMapping("/{email}")
    public List<Notification> getNotifications(@PathVariable String email) {
        return notificationRepository.findByRecipientEmail(email);
    }

}