package com.ravindra.commercex.notification.mapper;

import com.ravindra.commercex.notification.dto.NotificationResponse;
import com.ravindra.commercex.notification.entity.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponseList(List<Notification> notifications);

}
