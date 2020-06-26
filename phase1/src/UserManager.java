import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserManager {
    //author: Jinyu Liu in group 0110 for CSC207H1 summer 2020 project

    protected ArrayList<Trade> transactions; // list of all transactions this User has completed since creation - Mel

    public ArrayList<User> listUsers;

    protected ArrayList<Trade> requestQueue; // this stays protected because only the User needs it - Mel

    //A stack of trade requests. Queue at the end and dequeue at index 0.
    private ArrayList<Trade> tradeRequestQueue;

    public ArrayList<User> getListUsers() {
        return this.listUsers;}

    public void createUser() {} //TODO Call the constructor for User, then append to listUsers

    public void sendTradeRequest(User user1, User user2, //user1 is the one sending the request to user2
                                 ArrayList<Item> itemsSentToUser1, ArrayList<Item> itemsSentToUser2,
                                 LocalDateTime timeOfTrade, String meetingPlace) throws CannotBorrowException {
        Trade trade = new Trade(user1, user2, itemsSentToUser1, itemsSentToUser2);
        this.requestQueue.add(trade);
        trade.setTimeOfTrade(timeOfTrade);
        trade.setMeetingPlace(meetingPlace);
        trade.user1TradeConfirmed = true;
    } //Does not remove item from user1 availableItems or user2 availableItems

    public void acceptTradeRequest(Trade trade, User user) {
        if (trade.getUser1() == user) {
            trade.user1AcceptedRequest = true;
        }
        else if (trade.getUser2() == user) {
            trade.user2AcceptedRequest = true;
        }
    }

    public void editTradeRequest(Trade trade, LocalDateTime timeOfTrade, String meetingPlace, User userEditing) {
        trade.timeOfTrade = timeOfTrade;
        trade.meetingPlace = meetingPlace;
        if (userEditing == trade.user1) {
            trade.user1AcceptedRequest = true;
            trade.user2.requestQueue.add(trade);
            trade.user2AcceptedRequest = false;
        }
        else if (userEditing == trade.user2) {
            trade.user2AcceptedRequest = true;
            trade.user1.requestQueue.add(trade);
            trade.user1AcceptedRequest = false;
        }
    }

    public void sendValidationRequest(String name, String description, String id, User owner) {
        Item item = new Item(name, description, id, owner);
        AdminUser.itemValidationQueue.add(new ItemValidationRequest(owner, item, description));
    }

    public void sendValidationRequest(String name, String id, User owner) {
        Item item = new Item(name, id, owner);
        AdminUser.itemValidationQueue.add(new ItemValidationRequest(owner, item));
    }

    public void AddTransaction(Trade trade){
        transactions.add(trade);
    } // This is for adding completed transactions to the stored list - Mel

    public ArrayList<Trade> RecentTransactions(){
        ArrayList<Trade> recents = new ArrayList<Trade>();
        if (transactions.size() > 3){
            recents.add(transactions.get(transactions.size()-1)); // most recent
            recents.add(transactions.get(transactions.size()-2)); // second most-recent
            recents.add(transactions.get(transactions.size()-3)); // third most-recent
            return recents;
        }
        else {return transactions;}
    }
    // most recent 3 transactions, access transactions list and take last 3
    // code for case where User hasn't traded w 3 ppl yet -Mel

    public Trade dequeueTradeRequest(){
        return this.tradeRequestQueue.remove(0);
    }
}
