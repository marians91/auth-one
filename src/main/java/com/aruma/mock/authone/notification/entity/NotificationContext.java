package com.aruma.mock.authone.notification.entity;

import com.aruma.mock.authone.notification.strategy.NotificationStrategy;

public class NotificationContext {
    private NotificationStrategy notificationStrategy;

    public NotificationContext(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public void executeStrategy(AccessInfo accessInfo) {
        notificationStrategy.sendNotification(accessInfo);
    }
}
