package kz.javazhan.sigma_finance.bridge;

import org.aspectj.weaver.ast.Not;

public class PromoNotification extends Notification {
    public PromoNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void notify(String to, String body) {
        sender.send(to,"[PROMO] "+body);
    }
}
