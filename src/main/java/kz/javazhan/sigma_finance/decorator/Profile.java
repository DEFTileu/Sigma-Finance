package kz.javazhan.sigma_finance.decorator;



public class Profile {
    String name;
    String email;
    String profileUrl;


    Profile(String name,String email, String profileUrl){
        this.name = name;
        this.email = email;
        this.profileUrl = profileUrl;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                '}';
    }
}
