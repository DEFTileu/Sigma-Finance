package kz.javazhan.sigma_finance.bridge;

public interface MessageSender {
    void send(String to, String body);

}
