package kz.javazhan.sigma_finance.config;

public class Singleton {
    private Singleton singleton;
    private String someValues;


    private Singleton(){}

    public Singleton getInstance(){
        if (singleton == null){
            singleton = new Singleton();
            return this.singleton;
        }else {
            return this.singleton;
        }
    }

    public String getSomeValues(){
        return this.someValues;
    }
}
