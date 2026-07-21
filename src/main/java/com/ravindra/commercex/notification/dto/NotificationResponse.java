package com.ravindra.commercex.notification.dto;

import com.ravindra.commercex.notification.enums.NotificationChannel;
import com.ravindra.commercex.notification.enums.NotificationStatus;
import com.ravindra.commercex.notification.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(

    Long id,

    NotificationType type,

    NotificationChannel channel,

    String title,

    String message,

    NotificationStatus status,

    LocalDateTime createdAt

) {
}
