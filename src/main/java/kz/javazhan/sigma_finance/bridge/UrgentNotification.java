package kz.javazhan.sigma_finance.bridge;

public class UrgentNotification extends Notification{

    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void notify(String to, String body) {
        sender.send(to,"[URGENT] "+body);
    }

}
