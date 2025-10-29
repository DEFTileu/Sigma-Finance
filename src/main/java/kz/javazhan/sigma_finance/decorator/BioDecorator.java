package kz.javazhan.sigma_finance.decorator;

public class BioDecorator extends ProfileDecorator{
    private String bio;
    BioDecorator(Profile profile, String bio) {
        super(profile);
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "BioDecorator{" +
                "bio='" + bio + '\'' +
                '}';
    }
}


