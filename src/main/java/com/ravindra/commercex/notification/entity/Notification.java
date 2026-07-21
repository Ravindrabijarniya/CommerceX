package com.ravindra.commercex.notification.entity;


import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.notification.enums.NotificationChannel;
import com.ravindra.commercex.notification.enums.NotificationStatus;
import com.ravindra.commercex.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(
    name = "notifications",
    indexes = {
        @Index(
            name = "idx_notification_user",
            columnList = "user_id"
        ),
        @Index(
            name = "idx_notification_status",
            columnList = "status"
        )
    }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {


    @Column(
        name = "user_id",
        nullable = false
    )
    private Long userId;


    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false
    )
    private NotificationType type;


    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false
    )
    private NotificationChannel channel;


    @Column(
        nullable = false
    )
    private String title;


    @Column(
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String message;


    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false
    )
    private NotificationStatus status;



    public static Notification create(
        Long userId,
        NotificationType type,
        NotificationChannel channel,
        String title,
        String message
    ){

        return Notification.builder()

            .userId(
                Objects.requireNonNull(
                    userId,
                    "User id cannot be null"
                )
            )

            .type(
                Objects.requireNonNull(
                    type,
                    "Notification type cannot be null"
                )
            )

            .channel(
                Objects.requireNonNull(
                    channel,
                    "Notification channel cannot be null"
                )
            )

            .title(
                Objects.requireNonNull(
                    title,
                    "Notification title cannot be null"
                )
            )

            .message(
                Objects.requireNonNull(
                    message,
                    "Notification message cannot be null"
                )
            )

            .status(NotificationStatus.PENDING)

            .build();
    }



    public void markAsSent(){

        this.status = NotificationStatus.SENT;
    }



    public void markAsFailed(){

        this.status = NotificationStatus.FAILED;
    }


}
