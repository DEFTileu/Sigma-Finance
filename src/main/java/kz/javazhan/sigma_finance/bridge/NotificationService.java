package kz.javazhan.sigma_finance.bridge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailSender emailSender;
    private final SmsSender smsSender;

    public void sendUrgentByEmail(String to, String body) {
        Notification notification = new UrgentNotification(emailSender);
        notification.notify(to, body);
    }

    public void sendPromoBySms(String to, String body) {
        Notification notification = new PromoNotification(smsSender);
        notification.notify(to, body);
    }
}

