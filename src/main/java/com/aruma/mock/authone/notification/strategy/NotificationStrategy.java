package com.aruma.mock.authone.notification.strategy;

import com.aruma.mock.authone.notification.entity.AccessInfo;

public interface NotificationStrategy {

    public void sendNotification(AccessInfo accessInfo);
}
