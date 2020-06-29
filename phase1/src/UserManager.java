import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;

public class UserManager implements Serializable{
    //author: Jinyu Liu, Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    //All methods written by Jinyu Liu except where noted

    protected ArrayList<Trade> transactions; // list of all transactions this User has completed since creation - Mel

    protected ArrayList<Trade> completedTrades; // list of all trades which have been completed - Louis

    protected static ArrayList<User> listUsers; // List of all users - Jinyu

    protected ArrayList<Trade> pendingTradeRequests; // list of all trade requests which have not been accepted
    // by both parties - Louis


    protected ArrayList<Trade> pendingTrades; // list of all trades which have been accepted but not completed - Louis

    protected ArrayList<TemporaryTrade> currentTemporaryTrades; //list of all temporary trades where items have
    // been exchanged but not returned -Louis

    public ArrayList<User> getListUsers() {
        return UserManager.listUsers;}

    public ArrayList<Trade> getCompletedTrades() {
        return completedTrades;
    }

    public ArrayList<Trade> getPendingTrades() {
        return pendingTrades;
    }

    public ArrayList<Trade> getPendingTradeRequests() {
        return pendingTradeRequests;
    }

    public static ArrayList<ItemValidationRequest> itemValidationRequestQueue;

    /** Method which creates a user and adds it to the list of users
     * Author: Jinyu Liu
     * @param username username of user
     * @param password password of user
     */
    public void createUser(String username, String password) {
        User newUser = new User(username);
        newUser.setPassword(password);
        listUsers.add(newUser);
    }

    //TODO: Make it so this method depends on item IDs (Integer) and not directly on items.
    /** Method which creates a trade request and adds it to the list of pending trade requests.
     * Author: Jinyu Liu
     * Slight rework by Louis Scheffer V 6/27/2020
     * @param user1 user1
     * @param user2 user2
     * @param itemIDsSentToUser1 list of IDs of items which will be sent to user1
     * @param itemIDsSentToUser2 list of IDs of items which will be sent to user2
     * @param timeOfTrade time & date of the trade
     * @param meetingPlace location of the trade
     */
    public void sendTradeRequest(User user1, User user2, //user1 is the one sending the request to user2.
                                 ArrayList<Integer> itemIDsSentToUser1, ArrayList<Integer> itemIDsSentToUser2,
                                 LocalDateTime timeOfTrade, String meetingPlace) {
        int tradeCapacity = 1;

        ArrayList<Integer> list1;
        list1 = (ArrayList<Integer>)itemIDsSentToUser1.subList(0, tradeCapacity);
        ArrayList<Integer> list2;
        list2 = (ArrayList<Integer>)itemIDsSentToUser2.subList(0, tradeCapacity);

        Trade trade = new Trade(user1.username, user2.username, list1, list2);
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
        if (userEditing.username.equals(trade.getUsername1())) {
            trade.user1AcceptedRequest = true;
            trade.user2AcceptedRequest = false;
            trade.incrementUser1NumRequests();
        }
        else if (userEditing.username.equals(trade.getUsername2())) {
            trade.user2AcceptedRequest = true;
            trade.user1AcceptedRequest = false;
            trade.incrementUser2NumRequests();
        }
    }

    /** 3-arg method which creates and instantiates an ItemvalidationRequest.
     * Author: Jinyu Liu
     * @param name name of the item
     * @param description description of the item
     * @param owner username of the user who will own the item
     */
    public void sendValidationRequest(String name, String description, String owner) {
        // reworked by Tingyu since the itemValidationRequestQueue has been moved to UserManager
        itemValidationRequestQueue.add(new ItemValidationRequest(owner, name, description));
    }

    /** 2-arg method which creates and instantiates an ItemvalidationRequest.
     * Author: Jinyu Liu
     * @param name name of the item
     * @param owner username of the user who will own the item
     */
    public void sendValidationRequest(String name, String owner) {
        itemValidationRequestQueue.add(new ItemValidationRequest(name, owner));
    }

    /* No longer neccessary - Louis
    public void AddTransaction(Trade trade){
        transactions.add(trade);
    } // This is for adding completed transactions to the stored list - Mel
     */

    //TODO fix this method and other stats methods
    public ArrayList<Trade> RecentTransactions(User user) {
        ArrayList<Trade> potentialRecent = new ArrayList<Trade>();
        for (Trade trade : completedTrades) {
            if (trade.getUsername1() == user.username & !trade.getItemIDsSentToUser1().isEmpty()) {
                potentialRecent.add(trade);
            } else if (trade.getUsername2() == user.username & !trade.getItemIDsSentToUser2().isEmpty()) {
                potentialRecent.add(trade);
            }
        }
    } //there can be many items traded per single trade, how do we keep track of top three?
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
    public static User searchUser(String username){
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
        return !(u1.getFrozen() || u1.getFrozen());
    }
    /** FYI: variable has been changed to use method checkPermission.
     * Author: Melody Yang
     */

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
            //TODO: if the borrowing user now has more borrows than loans + threshold, send
            // a freeze request to the adminUser (through the dispatching function).
            //TODO: if you're

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
            user2.removeItemFromList(item, user2.availableItems);
            if (trade instanceof TemporaryTrade){
                user1.addItemToList(item, user1.borrowedItems);
            }
            else {
                user1.addItemToList(item, user1.availableItems);
            }
        }
        for(int itemID : trade.getItemIDsSentToUser2()){
            Item item = searchItem(user1, itemID);
            //do borrowed and lent get incremented every trade or just during TemporaryTrades? - Louis
            user2.increaseStat("borrowed", 1);
            user1.increaseStat("lent", 1);
            user1.removeItemFromList(item, user1.availableItems);
            if (trade instanceof TemporaryTrade){
                user2.addItemToList(item, user2.borrowedItems);
            }
            else{
                user2.addItemToList(item, user2.availableItems);
            }

        }
    }
    /**
     * Check to see if any TemporaryTrades have expired and if so, add an alert to the User's alertQueue.
     * Author: Murray Smith
     */
    public void checkForExpiredTempTrades(){
        for (TemporaryTrade tempTrade : currentTemporaryTrades) {
            if (LocalDateTime.now().isAfter(tempTrade.getDueDate())) {
                User borrowingUser;
                String otherUserName;
                if (tempTrade.itemIDsSentToUser1.size() == 0){
                    borrowingUser = searchUser(tempTrade.getUsername2());
                    otherUserName = tempTrade.getUsername1();
                } else {
                    borrowingUser = searchUser(tempTrade.getUsername1());
                    otherUserName = tempTrade.getUsername2();
                }
                borrowingUser.alertQueue.add("Your items to " + otherUserName + " are due back, request to meet them?");
                //TODO: In the presenter layer, add an input to the user after this line is printed to prompt for "yes"
                // or "no" to the above question, the input should call another method to create this returnRequest.
                
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
                user1.removeItemFromList(item, user1.borrowedItems);
                user2.addItemToList(item, user2.availableItems);
            }
            for(int itemID : trade.getItemIDsSentToUser2()) {
                Item item = searchItem(user1, itemID);
                user2.removeItemFromList(item, user2.borrowedItems);
                user2.addItemToList(item, user2.availableItems);
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

    /**
     *
     * @param trade a trade object
     * @return a string which describes the two users involved in the trade and the Time & date of the trade.
     */
    public String tradeToString(Trade trade){
        return "User 1: " + trade.getUsername1() + "/nUser 2: " + trade.getUsername2() +
                "/nItems being traded from user 1 to user 2: " + GetItemNamesFromUser1ToUser2(trade) +
                "/nItems being traded from user 2 to user 1: " + GetItemNamesFromUser1ToUser2(trade) +
                "/n Time & Date of item exchange: " + trade.getTimeOfTrade().toString() +
                "/n Location of Trade: " + trade.getMeetingPlace();
    }
    // helper method which lists the names of the items going from user 1 to user 2 - Louis
    private String GetItemNamesFromUser1ToUser2(Trade trade){
        StringBuilder stringBuilder = new StringBuilder();
        for(int itemID: trade.getItemIDsSentToUser2()){
            Item item = searchItem(searchUser(trade.getUsername1()), itemID);
            stringBuilder.append(item.getName()).append(" ");
            return stringBuilder.toString();
        }
        return null;
    }
    // helper method which lists the names of the items going from user 2 to user 1 - Louis
    private String GetItemNamesFromUser2ToUser1(Trade trade){
        StringBuilder stringBuilder = new StringBuilder();
        for(int itemID: trade.getItemIDsSentToUser1()){
            Item item = searchItem(searchUser(trade.getUsername2()), itemID);
            stringBuilder.append(item.getName()).append(" ");
            return stringBuilder.toString();
        }
        return null;
    }
}
