import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class TemporaryTrade extends Trade implements Serializable {

    private LocalDateTime tradeUntil;

    private boolean user1ItemReturnRequestAccepted = false;
    private boolean user2ItemReturnRequestAccepted = false;

    //Two constructors, one that takes a date for the object to be returned and annother that sets it by
    //default to thirty days after the present.

    public TemporaryTrade(String username1, String username2, ArrayList<Integer> itemIDsSentToUser1,
                          ArrayList<Integer> itemIDsSentToUser2, LocalDateTime tradeUntil, int tradeID) {
        super(username1, username2, itemIDsSentToUser1, itemIDsSentToUser2, tradeID);
        this.tradeUntil = tradeUntil;
    }

    public TemporaryTrade(String username1, String username2, ArrayList<Integer> itemIDsSentToUser1,
                          ArrayList<Integer> itemIDsSentToUser2, int tradeID){
        super(username1, username2, itemIDsSentToUser1, itemIDsSentToUser2, tradeID);
        LocalDateTime now = LocalDateTime.now();
        this.tradeUntil = now.plusDays(30);
    }

    /**
     *
     * @return the time & date at which this temporary trade expires.
     */
    public LocalDateTime getDueDate(){
        return this.tradeUntil;
    }

    /**
     *
     * @param newDueDate the new time & date that this temporary trade will end.
     */
    public void setDueDate(LocalDateTime newDueDate){
        this.tradeUntil = newDueDate;
    }

    /**
     *
     * @return whether or not user1 has accepted the request to return the items.
     */
    public boolean getUser1ItemReturnRequestAccepted(){
        return this.user1ItemReturnRequestAccepted;
    }

    /**
     *
     * @return whether or not user2 has accepted the request to return the items.
     */
    public boolean getUser2ItemReturnRequestAccepted(){
        return this.user2ItemReturnRequestAccepted;
    }

    /**
     *
     * @param user1ItemReturnRequestAccepted whether or not user1 has accepted the request to return the items.
     */
    public void setUser1ItemReturnRequestAccepted(boolean user1ItemReturnRequestAccepted){
        this.user1ItemReturnRequestAccepted = user1ItemReturnRequestAccepted;
    }

    /**
     *
     * @param user2ItemReturnRequestAccepted whether or not user2 has accepted the request to return the items.
     */
    public void setUser2ItemReturnRequestAccepted(boolean user2ItemReturnRequestAccepted){
        this.user2ItemReturnRequestAccepted = user2ItemReturnRequestAccepted;
    }
}
