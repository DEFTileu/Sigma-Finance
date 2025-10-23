package kz.javazhan.sigma_finance.bridge;

import org.springframework.stereotype.Component;

@Component
public class SmsSender implements MessageSender{
    @Override
    public void send(String to, String body) {
        System.out.println("sms sent to " + to + " with body: " + body);
    }
}
