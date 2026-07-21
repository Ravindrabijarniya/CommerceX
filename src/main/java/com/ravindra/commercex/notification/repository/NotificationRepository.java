package com.ravindra.commercex.notification.repository;

import com.ravindra.commercex.notification.entity.Notification;
import com.ravindra.commercex.notification.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findByStatus(NotificationStatus status);

}
