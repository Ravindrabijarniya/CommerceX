package com.ravindra.commercex.notification.controller;

import com.ravindra.commercex.notification.dto.NotificationResponse;
import com.ravindra.commercex.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getMyNotifications() {

        return ResponseEntity.ok(
            notificationService.getMyNotifications()
        );
    }
}
