import java.util.ArrayList;
import java.time.LocalDateTime;

public class TemporaryTrade extends Trade{

    private LocalDateTime tradeUntil;

    //Two constructors, one that takes a date for the object to be returned and annother that sets it by
    //default to thirty days after the present.

    public TemporaryTrade(User user1, User user2, ArrayList<Item> itemsSentToUser1,
                          ArrayList<Item> itemsSentToUser2, LocalDateTime tradeUntil) throws CannotBorrowException {
        super(user1, user2, itemsSentToUser1, itemsSentToUser2);
        this.tradeUntil = tradeUntil;
    }

    public TemporaryTrade(User user1, User user2, ArrayList<Item> itemsSentToUser1,
                          ArrayList<Item> itemsSentToUser2) throws CannotBorrowException {
        super(user1, user2, itemsSentToUser1, itemsSentToUser2);
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
