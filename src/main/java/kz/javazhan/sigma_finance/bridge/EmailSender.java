package kz.javazhan.sigma_finance.bridge;

import org.springframework.stereotype.Component;

@Component
public class EmailSender implements MessageSender {
    @Override
    public void send(String to, String body) {
        System.out.println("email sent to " + to + " with body: " + body);
    }
}
