package kz.javazhan.sigma_finance.bridge;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Notification {
    protected final MessageSender sender;

    public abstract void notify(String to, String body);
}
