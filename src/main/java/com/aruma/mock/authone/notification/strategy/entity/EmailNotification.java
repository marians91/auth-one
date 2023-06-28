package com.aruma.mock.authone.notification.strategy.entity;

import lombok.extern.slf4j.Slf4j;
import com.aruma.mock.authone.notification.entity.AccessInfo;
import com.aruma.mock.authone.notification.strategy.NotificationStrategy;
@Slf4j
public class EmailNotification implements NotificationStrategy {

    @Override
    public void sendNotification(AccessInfo accessInfo) {
        log.info("Sending notification to email address.");
    }
}
