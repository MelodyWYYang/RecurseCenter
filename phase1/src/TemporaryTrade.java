import java.util.ArrayList;
import java.time.LocalDateTime;

public class TemporaryTrade extends Trade{

    private LocalDateTime tradeUntil;

    //Two constructors, one that takes a date for the object to be returned and annother that sets it by
    //default to thirty days after the present.

    public TemporaryTrade(String username1, String username2, ArrayList<Integer> itemIDsSentToUser1,
                          ArrayList<Integer> itemIDsSentToUser2, LocalDateTime tradeUntil) {
        super(username1, username2, itemIDsSentToUser1, itemIDsSentToUser2);
        this.tradeUntil = tradeUntil;
    }

    public TemporaryTrade(String username1, String username2, ArrayList<Integer> itemIDsSentToUser1,
                          ArrayList<Integer> itemIDsSentToUser2){
        super(username1, username2, itemIDsSentToUser1, itemIDsSentToUser2);
        LocalDateTime now = LocalDateTime.now();
        this.tradeUntil = now.plusDays(30);
    }

    public LocalDateTime getDueDate(){
        return this.tradeUntil;
    }

    public void setDueDate(LocalDateTime newDueDate){
        this.tradeUntil = newDueDate;
    }
}
