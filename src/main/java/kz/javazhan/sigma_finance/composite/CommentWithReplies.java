package kz.javazhan.sigma_finance.composite;

import java.util.ArrayList;
import java.util.List;

public class CommentWithReplies implements CommentComponent{
    private String text;
    private List<CommentComponent> replies = new ArrayList<>();


    public CommentWithReplies(String text) {
        this.text = text;
    }

    public void addReply(CommentComponent comment){
        replies.add(comment);
    }

    public void removeReply(CommentComponent comment){
        replies.remove(comment);
    }


    @Override
    public void display(int lvl) {
        String bosOryn = " ".repeat(lvl*2);
        System.out.println(bosOryn+"Comment: "+text);
            for (CommentComponent reply : replies){
                reply.display(lvl+1);
            }
    }
}
