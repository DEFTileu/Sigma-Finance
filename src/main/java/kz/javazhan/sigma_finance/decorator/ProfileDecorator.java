package kz.javazhan.sigma_finance.decorator;

public class ProfileDecorator extends Profile{
    private final Profile profile;


    ProfileDecorator(Profile profile) {
        super(profile.name, profile.email, profile.profileUrl);
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfileDecorator{" +
                "profile=" + profile +
                '}';
    }
}
