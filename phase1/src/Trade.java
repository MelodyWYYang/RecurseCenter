import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Trade {
    //author: Murray Smith in group 0110 for CSC207H1 summer 2020 project

    protected User user1;
    protected User user2;

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

    protected ArrayList<Item> itemsSentToUser1;
    protected ArrayList<Item> itemsSentToUser2;

    public Trade(User user1, User user2,
                 ArrayList<Item> itemsSentToUser1, ArrayList<Item> itemsSentToUser2) throws CannotBorrowException{
        this.user1 = user1;
        this.user2 = user2;
        this.itemsSentToUser1 = itemsSentToUser1;
        this.itemsSentToUser2 = itemsSentToUser2;
        //The description defines a one-way trade as "permanently borrowing", so the borrow/lending ratio still applies.
        if(itemsSentToUser1.size() == 0 && user2.getNumBorrowed() < user2.getNumLent() + numLendsForBorrowThreshold){
            throw new CannotBorrowException("The receiving user has not lent enough!");
        } else if (itemsSentToUser2.size() == 0 && user1.getNumBorrowed() < user2.getNumLent() + numLendsForBorrowThreshold){
            throw new CannotBorrowException("The receiving user has not lent enough!");
        }
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

    public User getUser1(){
        return this.user1;
    }

    public User getUser2(){
        return this.user2;
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

    public ArrayList<Item> getItemsSentToUser1(){
        return (ArrayList<Item>)this.itemsSentToUser1.clone();
    }

    public ArrayList<Item> getItemsSentToUser2(){
        return (ArrayList<Item>)this.itemsSentToUser2.clone();
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
