package kz.javazhan.sigma_finance.config;

public class Settings implements Cloneable{
    private String someValues1;
    private String someValues2;

    public Settings(String someValues1, String someValues2){
        this.someValues1 = someValues1;
        this.someValues2 = someValues2;
    }


    public Settings clone(){
        return new Settings(someValues1,someValues2);
    }
}
