package com.ravindra.commercex.notification.service;

import com.ravindra.commercex.security.service.CurrentUserService;
import com.ravindra.commercex.notification.dto.NotificationResponse;
import com.ravindra.commercex.notification.entity.Notification;
import com.ravindra.commercex.notification.enums.NotificationChannel;
import com.ravindra.commercex.notification.enums.NotificationType;
import com.ravindra.commercex.notification.mapper.NotificationMapper;
import com.ravindra.commercex.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createNotification(
        Long userId,
        NotificationType type,
        NotificationChannel channel,
        String title,
        String message
    ) {

        System.out.println("Creating notification for user: " + userId);

        Notification notification = Notification.create(
            userId,
            type,
            channel,
            title,
            message
        );


//        notificationRepository.save(notification);
        notificationRepository.saveAndFlush(notification);

        System.out.println("Notification saved with id: " + notification.getId());
    }

    @Override
    public List<NotificationResponse> getMyNotifications() {

        Long userId = currentUserService.getCurrentUser().getId();

        return notificationMapper.toResponseList(
            notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
        );
    }
}
