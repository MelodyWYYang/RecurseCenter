import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;

public class UserManager implements Serializable{
    //author: Jinyu Liu, Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    //All methods written by Jinyu Liu except where noted

    protected ArrayList<Trade> completedTrades; // list of all trades which have been completed - Louis

    protected ArrayList<User> listUsers; // List of all users

    protected ArrayList<Trade> pendingTradeRequests; // list of all trade requests which have not been accepted
    // by both parties - Louis


    protected ArrayList<Trade> pendingTrades; // list of all trades which have been accepted but not completed - Louis

    protected ArrayList<TemporaryTrade> currentTemporaryTrades; //list of all temporary trades where items have
    // been exchanged but not returned -Louis

    public ArrayList<User> getListUsers() {
        return this.listUsers;}

    public ArrayList<Trade> getCompletedTrades() {
        return completedTrades;
    }

    public ArrayList<Trade> getPendingTrades() {
        return pendingTrades;
    }

    public ArrayList<Trade> getPendingTradeRequests() {
        return pendingTradeRequests;
    }

    /** Method which creates a user and adds it to the list of users
     * Author: Jinyu Liu
     * @param username
     * @param password
     */
    public void createUser(String username, String password) {
        User newUser = new User(username);
        newUser.setPassword(password);
        listUsers.add(newUser);
    }

    /** Method which creates a trade request and adds it to the list of pending trade requests.
     * Author: Jinyu Liu
     * Slight rework by Louis Scheffer V 6/27/2020
     * @param user1 user1
     * @param user2 user2
     * @param itemsSentToUser1 list of items which will be sent to user1
     * @param itemsSentToUser2 list of items which will be sent to user2
     * @param timeOfTrade time & date of the trade
     * @param meetingPlace location of the trade
     * @throws CannotBorrowException
     */
    public void sendTradeRequest(User user1, User user2, //user1 is the one sending the request to user2
                                 ArrayList<Item> itemsSentToUser1, ArrayList<Item> itemsSentToUser2,
                                 LocalDateTime timeOfTrade, String meetingPlace) throws CannotBorrowException {
        ArrayList<Integer> itemIDsSentToUser1 = new ArrayList<Integer>();
        for (int i = 0; i < itemsSentToUser1.size(); i++){
            itemIDsSentToUser1.add(itemsSentToUser1.get(i).getId());
        }
        ArrayList<Integer> itemIDsSentToUser2 = new ArrayList<Integer>();
        for (int i = 0; i < itemsSentToUser1.size(); i++){
            itemIDsSentToUser2.add(itemsSentToUser2.get(i).getId());
        }
        Trade trade = new Trade(user1.username, user2.username, itemIDsSentToUser1, itemIDsSentToUser2);
        pendingTradeRequests.add(trade);
        trade.setTimeOfTrade(timeOfTrade);
        trade.setMeetingPlace(meetingPlace);
        trade.user1TradeConfirmed = true;
    } //Does not remove item from user1 availableItems or user2 availableItems

    /** Method which allows a user to accept a trade request
     * Author: Jinyu Liu
     * @param trade trade object
     * @param user user which is accepting the trade request
     */
    public void acceptTradeRequest(Trade trade, User user) {
        if (trade.getUsername1().equals(user.username)) {
            trade.user1AcceptedRequest = true;
        }
        else if (trade.getUsername2().equals(user.username)) {
            trade.user2AcceptedRequest = true;
        }
    }

    /** Method which allows a user to counter-offer by changing the details of a trade request
     * Author: Jinyu Liu
     * slight rework by Louis Scheffer V 6/27/2020
     * @param trade trade object
     * @param timeOfTrade time & date of trade
     * @param meetingPlace location of trade
     * @param userEditing user who is editing the trade
     */
    public void editTradeRequest(Trade trade, LocalDateTime timeOfTrade, String meetingPlace, User userEditing) {
        trade.timeOfTrade = timeOfTrade;
        trade.meetingPlace = meetingPlace;
        if (userEditing.equals(trade.getUsername1())) {
            trade.user1AcceptedRequest = true;
            trade.user2AcceptedRequest = false;
            trade.incrementUser1NumRequests();
        }
        else if (userEditing.equals(trade.getUsername2())) {
            trade.user2AcceptedRequest = true;
            trade.user1AcceptedRequest = false;
            trade.incrementUser2NumRequests();
        }
    }

    /** 3-arg method which creates and instantiates an ItemvalidationRequest.
     *
     * @param name name of the item
     * @param description description of the item
     * @param owner username of the user who will own the item
     */
    public void sendValidationRequest(String name, String description, String owner) {
        AdminUser.itemValidationQueue.add(new ItemValidationRequest(owner, name, description));
    }

    /** 3-arg method which creates and instantiates an ItemvalidationRequest.
     * Author: Jinyu Liu
     * @param name name of the item
     * @param owner username of the user who will own the item
     */
    public void sendValidationRequest(String name, String owner) {
        AdminUser.itemValidationQueue.add(new ItemValidationRequest(name, owner));
    }
    /* No longer neccessary - Louis
    public void AddTransaction(Trade trade){
        transactions.add(trade);
    } // This is for adding completed transactions to the stored list - Mel
     */

    //TODO fix this method and other stats methods
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

    /* i don't think this is neccessary anymore - Louis
    public Trade dequeueTradeRequest(){
        return this.tradeRequestQueue.remove(0);
    }
     */

    /** Method which returns a user when given their username
     * Author: Louis Scheffer V
     * @param username username of the user
     * @return user object
     */
    public User searchUser(String username){
        for(User user: listUsers){
            if (user.username.equals(username)){
                return user;
            }
        }
        return null;
    }

    /** Method which returns a item (that is in a user's available items) when given its ID number and the user who it
     * belongs to. Returns null if the ID is invalid.
     * Author: Louis Scheffer V
     * @param user who owns the item
     * @param itemID ID number of the item
     * @return item
     */
    public Item searchItem(User user, int itemID){
        for(Item item: user.getAvailableItems()){
            if(itemID == item.getId()){
                return item;
            }
        }
        return null;
    }
    /** Method which returns a item (that is in a user's available items) when given its ID number.
     * Returns null if an invalid ID is given
     * Author: Louis Scheffer V
     * @param itemID ID number of the item
     * @return item
     */
    public Item searchItem(int itemID) {
        for (User user : listUsers) {
            for (Item item : user.getAvailableItems()) {
                if (itemID == item.getId()) {
                    return item;
                }
            }
        }
        return null;
    }

    /** Method which ensures that neither user account is frozen.
     * Author: Louis Scheffer V
     * @param u1 user1
     * @param u2 user2
     * @return Boolean
     */
    public Boolean beforeTrade(User u1, User u2){
        return u1.getPermission() && u2.getPermission();
    }

    /** Method which executes all item swaps and checks all pending trades and pending trade requests after a trade has
     * been completed. Trade is moved to completed trades if it is a
     * Author: Louis Scheffer V
     * @param trade trade object
      */
    public void afterTrade(Trade trade){
        exchangeItems(trade);
        checkPendingTradeRequests();
        checkPendingTrades();
        pendingTrades.remove(trade);
        if (trade instanceof TemporaryTrade){
            currentTemporaryTrades.add((TemporaryTrade) trade);
        }
        else{
            completedTrades.add(trade);
        }

    }

    /** Method which exchanges the items in the trade system after a trade has been marked as completed
     * Author: Louis Scheffer V
     * @param trade trade object
     */
    public void exchangeItems(Trade trade){
        User user1 = searchUser(trade.getUsername1());
        User user2 = searchUser(trade.getUsername2());
        for(int itemID : trade.getItemIDsSentToUser1()){
            Item item = searchItem(user2, itemID);
            //do borrowed and lent get incremented every trade or just during TemporaryTrades? - Louis
            user1.increaseStat("borrowed", 1);
            user2.increaseStat("lent", 1);
            user2.removeAvailableItem(item);
            if (trade instanceof TemporaryTrade){
                user1.addBorrowedItem(item);
            }
            else {
                user1.addAvailableItem(item);
            }
        }
        for(int itemID : trade.getItemIDsSentToUser2()){
            Item item = searchItem(user1, itemID);
            //do borrowed and lent get incremented every trade or just during TemporaryTrades? - Louis
            user2.increaseStat("borrowed", 1);
            user1.increaseStat("lent", 1);
            user1.removeAvailableItem(item);
            if (trade instanceof TemporaryTrade){
                user2.addBorrowedItem(item);
            }
            else{
                user2.addAvailableItem(item);
            }

        }
    }

    /** Method which returns items to their owners after the expiration of a temporary trade
     * Author: Louis Scheffer V
     * Design decision is neccessary: should items automatically return to their owner at the expiry of the trade?
     * @param trade
     */
    public void reExchangeItems(TemporaryTrade trade){
        if(LocalDateTime.now().isAfter(trade.getDueDate())){
            User user1 = searchUser(trade.getUsername1());
            User user2 = searchUser(trade.getUsername2());
            for(int itemID : trade.getItemIDsSentToUser1()) {
                Item item = searchItem(user2, itemID);
                user1.removeBorrowedItem(item);
                user2.addAvailableItem(item);
            }
            for(int itemID : trade.getItemIDsSentToUser2()) {
                Item item = searchItem(user1, itemID);
                user2.removeBorrowedItem(item);
                user2.addAvailableItem(item);
            }
        }
    }

    /** Method which checks all pending trades to see if the items are still available.If they are not then the trade
     * request is deleted.
     * Author: Louis Scheffer V
     */
    public void checkPendingTrades(){
        for(Trade trade: pendingTrades){
            User user1 = searchUser(trade.getUsername1());
            User user2 = searchUser(trade.getUsername2());
            for(int itemID : trade.getItemIDsSentToUser1()){
                Item item = searchItem(user2, itemID);
                if (item == null){
                    pendingTrades.remove(trade);
                    //in the future we might message the users involved here - Louis
                }
            }
            for(int itemID : trade.getItemIDsSentToUser2()) {
                Item item = searchItem(user1, itemID);
                if (item == null) {
                    pendingTrades.remove(trade);
                    //in the future we might message the users involved here - Louis
                }
            }
        }
    }
    /** Method which checks all pending trades to see if the items are still available. If they are not then the trade
     * request is deleted.
     * Author: Louis Scheffer V
     */
    public void checkPendingTradeRequests(){
        for(Trade trade: pendingTradeRequests){
            User user1 = searchUser(trade.getUsername1());
            User user2 = searchUser(trade.getUsername2());
            for(int itemID : trade.getItemIDsSentToUser1()){
                Item item = searchItem(user2, itemID);
                if (item == null){
                    pendingTradeRequests.remove(trade);
                    //in the future we might message the users involved here - Louis
                }
            }
            for(int itemID : trade.getItemIDsSentToUser2()) {
                Item item = searchItem(user1, itemID);
                if (item == null) {
                    pendingTradeRequests.remove(trade);
                    //in the future we might message the users involved here - Louis
                }
            }
        }
    }
}
