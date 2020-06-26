import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserManager {
    //author: Jinyu Liu in group 0110 for CSC207H1 summer 2020 project

    protected ArrayList<Trade> transactions; // list of all transactions this User has completed since creation - Mel

    public ArrayList<User> listUsers;

    public ArrayList<User> getListUsers() {
        return this.listUsers;}

    public void createUser() {} //TODO Call the constructor for User, then append to listUsers

    public void sendTradeRequest(User user1, User user2, //user1 is the one sending the request to user2
                                 ArrayList<Item> itemsSentToUser1, ArrayList<Item> itemsSentToUser2,
                                 LocalDateTime timeOfTrade, String meetingPlace) throws CannotBorrowException {
        Trade trade = new Trade(user1, user2, itemsSentToUser1, itemsSentToUser2);
        user2.requestQueue.add(trade);
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
    }

    public ArrayList RecentTransactions(){
        ArrayList<Trade> recents = new ArrayList<Trade>();
        if (transactions.size() > 3){
            recents.add(transactions.get(transactions.size()-1)); // most recent
            recents.add(transactions.get(transactions.size()-2)); // second most-recent
            recents.add(transactions.get(transactions.size()-3)); // third most-recent
            return recents;
        }
        else {return transactions;}
    } // -Mel
}
