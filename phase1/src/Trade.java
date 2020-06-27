import java.time.LocalDateTime;
import java.util.ArrayList;

public class Trade {
    //author: Murray Smith in group 0110 for CSC207H1 summer 2020 project

    protected String username1;
    protected String username2;

    //I read in the assignment details that the AdminUser should be able to
    //change how many times one should have to lend in order to borrow.
    //I made this public static so it can be changed from anywhere and independent of
    //individual trades. - Murray
    public static int numLendsForBorrowThreshold = 1;

    protected boolean user1AcceptedRequest = false;
    protected boolean user2AcceptedRequest = false;

    protected boolean user1TradeConfirmed = false;
    protected boolean user2TradeConfirmed = false;

    protected int user1NumRequests = 0;
    protected int user2NumRequests = 0;

    protected LocalDateTime timeOfTrade;

    protected String meetingPlace;

    protected ArrayList<String> itemIDsSentToUser1;
    protected ArrayList<String> itemIDsSentToUser2;

    public Trade(String username1, String username2,
                 ArrayList<String> itemIDsSentToUser1, ArrayList<String> itemIDsSentToUser2){
        this.username1 = username1;
        this.username2 = username2;
        this.itemIDsSentToUser1 = itemIDsSentToUser1;
        this.itemIDsSentToUser2 = itemIDsSentToUser2;
    }

    /**
     * Return whether or not the trade has been accepted and confirmed by both parties. The trade is still
     * considered a trade request while this returns false.
     * @return A boolean determining whether or not the trade is completed and items have been exchanged.
     */
    public boolean isTradeCompleted(){
        return user1AcceptedRequest && user2AcceptedRequest && user1TradeConfirmed && user2TradeConfirmed;
    }

    /**
     * Return whether or not the trade has been accepted by both parties. The this returns false while
     * the two parties are still negotiating the trade.
     * @return A boolean determining whether or not the trade request is accepted by both parties.
     */
    public boolean isTradeRequestAccepted(){
        return user1AcceptedRequest && user2AcceptedRequest;
    }

    //Below is an assortment of getters and setters.

    public String getUsername1(){
        return this.username1;
    }

    public String getUsername2(){
        return this.username2;
    }

    public void setUser1AcceptedRequest(boolean user1AcceptedRequest) {
        this.user1AcceptedRequest = user1AcceptedRequest;
    }

    public void setUser2AcceptedRequest(boolean user2AcceptedRequest){
        this.user2AcceptedRequest = user2AcceptedRequest;
    }

    public void setUser1TradeConfirmed(boolean user1TradeConfirmed){
        this.user1TradeConfirmed = user1TradeConfirmed;
    }

    public void setUser2TradeConfirmed(boolean user2TradeConfirmed){
        this.user2TradeConfirmed = user2TradeConfirmed;
    }

    public void incrementUser1NumRequests(){
        user1NumRequests+=1;
    }

    public void incrementUser2NumRequests(){
        user2NumRequests+=1;
    }

    public ArrayList<String> getItemIDsSentToUser1(){
        return (ArrayList<String>)this.itemIDsSentToUser1.clone();
    }

    public ArrayList<String> getItemIDsSentToUser2(){
        return (ArrayList<String>)this.itemIDsSentToUser2.clone();
    }

    public LocalDateTime getTimeOfTrade(){
        return this.timeOfTrade;
    }
    public void setTimeOfTrade(LocalDateTime newTimeOfTrade){
        this.timeOfTrade = newTimeOfTrade;
    }

    public String getMeetingPlace(){
        return this.meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace){
        this.meetingPlace = meetingPlace;
    }


}
