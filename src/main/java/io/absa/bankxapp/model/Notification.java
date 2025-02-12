package io.absa.bankxapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Constructors
    public Notification() {
        this.timestamp = LocalDateTime.now();
    }

    public Notification(NotificationType type, String message, String recipientEmail) {
        this();
        this.type = type;
        this.message = message;
        this.recipientEmail = recipientEmail;
    }
    // Getters and Setters
    public Long getId() { return id; }

    public NotificationType getType() { return type; }

    public void setType(NotificationType type) { this.type = type; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getRecipientEmail() { return recipientEmail; }

    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

}