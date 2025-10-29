package kz.javazhan.sigma_finance.decorator;

import java.util.Arrays;

public class SocialMediaDecorator extends ProfileDecorator{
    String[] socialMediaLinks;

    SocialMediaDecorator(Profile profile, String[] links) {
        super(profile);
        this.socialMediaLinks = links;
    }

    @Override
    public String toString() {
        return "SocialMediaDecorator{" +
                "socialMediaLinks=" + Arrays.toString(socialMediaLinks) +
                '}';
    }
}
