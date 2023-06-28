package com.aruma.mock.authone.notification.factory;

import com.aruma.mock.authone.core.utils.enums.NotificationMode;
import com.aruma.mock.authone.notification.strategy.NotificationStrategy;
import com.aruma.mock.authone.notification.strategy.entity.EmailNotification;
import com.aruma.mock.authone.notification.strategy.entity.SlackNotification;
import com.aruma.mock.authone.notification.strategy.entity.SmsNotification;
import com.aruma.mock.authone.notification.strategy.entity.WhatsAppNotification;

public class NotificationFactory {
    public static NotificationStrategy supplyStrategy(NotificationMode mode) {

        switch (mode) {
            case SMS -> {
                return new SmsNotification();
            }
            case SLACK -> {
                return new SlackNotification();
            }
            case WHATSAPP -> {
                return new WhatsAppNotification();
            }
            default -> {
                return new EmailNotification();
            }
        }
    }
}
