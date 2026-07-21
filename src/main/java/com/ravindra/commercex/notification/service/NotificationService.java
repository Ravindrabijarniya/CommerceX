package com.ravindra.commercex.notification.service;

import com.ravindra.commercex.notification.dto.NotificationResponse;
import com.ravindra.commercex.notification.enums.NotificationChannel;
import com.ravindra.commercex.notification.enums.NotificationType;

import java.util.List;

public interface NotificationService {

    void createNotification(
        Long userId,
        NotificationType type,
        NotificationChannel channel,
        String title,
        String message
    );

    List<NotificationResponse> getMyNotifications();

}
