package kz.javazhan.sigma_finance.composite;

public class TextComment implements CommentComponent{
    private String text;

    public TextComment(String comment){
        this.text = comment;
    }


    @Override
    public void display(int lvl) {
        String bosOryn = " ".repeat(lvl*2);
        System.out.println(bosOryn+"Comment: "+text);
    }
}
