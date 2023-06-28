package com.aruma.mock.authone.notification.strategy.entity;

import com.aruma.mock.authone.notification.entity.AccessInfo;
import com.aruma.mock.authone.notification.strategy.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsNotification implements NotificationStrategy {
    @Override
    public void sendNotification(AccessInfo accessInfo) {
        log.info("Sending notification as SMS.");
    }
}
