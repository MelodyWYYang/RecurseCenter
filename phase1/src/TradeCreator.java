import AlertPack.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TradeCreator implements Serializable {

    protected ArrayList<Trade> pendingTrades = new ArrayList<Trade>(); // list of all trades which have been accepted but not completed - Louis


    protected ArrayList<Trade> pendingTradeRequests = new ArrayList<Trade>(); // list of all trade requests which have not been accepted
    // by both parties - Louis

    protected ArrayList<AdminAlert> adminAlerts = new ArrayList<AdminAlert>();

    protected HashMap<String, ArrayList<UserAlert>> userAlertsToDispatch = new HashMap<String, ArrayList<UserAlert>>();

    protected TradeHistories tradeHistories = new TradeHistories();

    private int completeThreshold; // # of complete trades allowed per week

    private int borrowLendThreshold = 1;

    private int tradeIdGenerator = 1;


    public ArrayList<Trade> getPendingTrades() {
        return pendingTrades;
    }

    public ArrayList<Trade> getPendingTradeRequests() {
        return pendingTradeRequests;
    }

    public HashMap<String, ArrayList<UserAlert>> fetchUserAlerts(){
        return tradeHistories.fetchUserAlerts();
    }

    /**
     * Gets all admin alerts from TradeCreator and TradeHistories. Also empties the adminAlerts lists.
     * @return an arraylist containing all admin alerts from this class and TradeHistories.
     */
    public ArrayList<AdminAlert> fetchAdminAlerts(){
        ArrayList<AdminAlert> alerts = this.adminAlerts;
        this.adminAlerts = new ArrayList<AdminAlert>();
        alerts.addAll(tradeHistories.fetchAdminAlerts());
        return alerts;
    }


    /** Method which creates a trade request and adds it to the list of pending trade requests.
     * Author: Jinyu Liu
     * Slight rework by Louis Scheffer V 6/27/2020
     * @param user1 user1
     * @param user2 user2
     * @param itemIDsSentToUser1 list of IDs of items which will be sent to user1
     * @param itemIDsSentToUser2 list of IDs of items which will be sent to user2
     * @param timeOfTrade time & date of the trade
     * @param meetingPlace location of the trade
     */ //TradeManager
    public Boolean sendTradeRequest(User user1, User user2, //user1 is the one sending the request to user2.
                                    ArrayList<Integer> itemIDsSentToUser1, ArrayList<Integer> itemIDsSentToUser2,
                                    LocalDateTime timeOfTrade, String meetingPlace) {

        if (!beforeTrade(user1, user2)) {
            return false;
        }
        ArrayList<Integer> list1;
        if (itemIDsSentToUser1.size() == 0){
            list1 = new ArrayList<Integer>();
        } else {
            list1 =  itemIDsSentToUser1;
        }
        ArrayList<Integer> list2;
        if (itemIDsSentToUser2.size() == 0){
            list2 = new ArrayList<Integer>();
        }else {
            list2 =  itemIDsSentToUser2;
        }

        Trade trade = new Trade(user1.username, user2.username, list1, list2, tradeIdGenerator);
        tradeIdGenerator++;
        pendingTradeRequests.add(trade);
        trade.setTimeOfTrade(timeOfTrade);
        trade.setMeetingPlace(meetingPlace);
        trade.user1TradeConfirmed = true;

        //Creating and adding an alert for user2
        TradeRequestAlert alert = createTradeRequestAlert(trade, user1, false);
        alertUser(user2, alert);
        return true;

    } //Does not remove item from user1 availableItems or user2 availableItems


    /** Method which creates a temporary trade request and adds it to the list of pending trade requests.
     * Author: Jinyu Liu
     * Slight rework by Louis Scheffer V 6/27/2020
     * @param user1 user1
     * @param user2 user2
     * @param itemIDsSentToUser1 list of IDs of items which will be sent to user1
     * @param itemIDsSentToUser2 list of IDs of items which will be sent to user2
     * @param timeOfTrade time & date of the trade
     * @param meetingPlace location of the trade
     */
    public Boolean sendTemporaryTradeRequest(User user1, User user2, //user1 is the one sending the request to user2.
                                    ArrayList<Integer> itemIDsSentToUser1, ArrayList<Integer> itemIDsSentToUser2,
                                    LocalDateTime timeOfTrade, String meetingPlace) {

        if (!beforeTrade(user1, user2)) {
            return false;
        }
        ArrayList<Integer> list1;
        if (itemIDsSentToUser1.size() == 0){
            list1 = new ArrayList<Integer>();
        } else {
            list1 =  itemIDsSentToUser1;
        }
        ArrayList<Integer> list2;
        if (itemIDsSentToUser2.size() == 0){
            list2 = new ArrayList<Integer>();
        }else {
            list2 =  itemIDsSentToUser2;
        }

        TemporaryTrade trade = new TemporaryTrade(user1.username, user2.username, list1, list2,
                LocalDateTime.now().plusDays(30), tradeIdGenerator);
        tradeIdGenerator++;
        pendingTradeRequests.add(trade);
        trade.setTimeOfTrade(timeOfTrade);
        trade.setMeetingPlace(meetingPlace);
        trade.user1TradeConfirmed = true;

        //Creating and adding an alert for user2
        TradeRequestAlert alert = createTradeRequestAlert(trade, user1, true);
        alertUser(user2, alert);
        return true;

    } //Does not remove item from user1 availableItems or user2 availableItems

    /**
     * Creates a TradeRequstAlert for <trade> object sent by <user1>. This method does not add said TradeRequestAlert
     *  to the alertSystem.
     * @param trade the Trade object for the Alert.
     * @param user1 the User who is sending the Trade request.
     * @return A TradeRequestAlert corresponding to <trade>
     */ //TradeManager
    public TradeRequestAlert createTradeRequestAlert(Trade trade, User user1, boolean isTempTrade){
        return new TradeRequestAlert(user1.getUsername(), trade.getTradeID(), isTempTrade);
    }

    /** Method which allows a user to accept a trade request
     * Author: Jinyu Liu
     * Reworked by Tingyu Liang 7/2/2020
     * @param trade trade object
     * @param username username of User which is accepting the trade request
     */ //TradeManager
    public void acceptTradeRequest(Trade trade, String username) {
        String otherUserName;

        if (trade.getUsername1().equals(username)) {
            trade.setUser1AcceptedRequest(true);
            otherUserName = trade.getUsername2();
        }
        else {
            // I am not sure if we have function to check if user is in the trade, commented by Tingyu
            assert trade.getUsername2().equals(username);
            trade.setUser2AcceptedRequest(true);
            otherUserName = trade.getUsername1();
        }
        pendingTradeRequests.remove(trade);
        pendingTrades.add(trade);

        TradeAcceptedAlert alert = new TradeAcceptedAlert(username, trade.getTradeID());
        alertUser(otherUserName, alert);
    }


    /**
     * Decline trade request <trade> sent to User <user>. Also sends a TradeDeclinedAlert to send to the requesting
     * User.
     * @param trade The trade request to be declined.
     * @param decliningUser username of The User declining the request.
     *///TradeManager
    public void declineTradeRequest(Trade trade, String decliningUser){

        pendingTradeRequests.remove(trade);

        TradeDeclinedAlert alert = new TradeDeclinedAlert(decliningUser, trade.getTradeID());

        String otherUserName;

        if (trade.getUsername1().equals(decliningUser)){
            otherUserName = trade.getUsername2();
        }else{
            otherUserName = trade.getUsername1();
        }
        alertUser(otherUserName, alert);
    }


    /** Method which allows a user to counter-offer by changing the details of a trade request
     * Author: Jinyu Liu
     * slight rework by Louis Scheffer V 6/27/2020
     * @param trade trade object
     * @param timeOfTrade time & date of trade
     * @param meetingPlace location of trade
     * @param UserEditingName  username of user who is editing the trade
     */ //TradeManager
    public void editTradeRequest(UserManager userManager, Trade trade, LocalDateTime timeOfTrade,
                                 String meetingPlace, String UserEditingName) {
        trade.timeOfTrade = timeOfTrade;
        trade.meetingPlace = meetingPlace;
        if (UserEditingName.equals(trade.getUsername1())) {
            trade.user1AcceptedRequest = true;
            trade.user2AcceptedRequest = false;
            trade.incrementUser1NumRequests();
        }
        else if (UserEditingName.equals(trade.getUsername2())) {
            trade.user2AcceptedRequest = true;
            trade.user1AcceptedRequest = false;
            trade.incrementUser2NumRequests();
        }

        TradeRequestAlert alert = createTradeRequestAlert(trade, userManager.searchUser(UserEditingName),
                trade instanceof TemporaryTrade);

        String otherUserName;

        if (trade.getUsername1().equals(UserEditingName)){
            otherUserName = trade.getUsername2();
        } else{
            otherUserName = trade.getUsername1();
        }

        alertUser(otherUserName, alert);
    }

    //TODO: decide if we still need this.
    private ArrayList<Trade> getIncompleteTrades() {
        ArrayList<Trade> incompleteTrades = new ArrayList<Trade>();
        for (Trade trade : pendingTrades) {
            if (trade.getTimeOfTrade().isAfter(LocalDateTime.now())) {
                incompleteTrades.add(trade);
            }
        }
        return incompleteTrades;
    }

    /** Number of incomplete trades made by the user
     * @param username username of User being evaluated
     * @return count of number of incomplete trades
     */ //TradeManager
    public int getNumIncompTrades(String username) {
        int count = 0;
        ArrayList<Trade> incompleteTrades = this.getIncompleteTrades();
        for (Trade trade : incompleteTrades) {
            if (trade.getUsername1().equals(username) | trade.getUsername2().equals(username)) {
                count++;
            }
        }
        return count;
    }


    /** Method which searches pending trade requests when given the trade's ID number and returns the trade.
     * Returns null if an invalid ID is given
     * @param tradeID ID number corresponding to the trade
     * @return the trade
     */ //TradeManager
    public Trade searchPendingTradeRequest(int tradeID){
        for (Trade trade : pendingTradeRequests){
            if (trade.getTradeID() == tradeID){
                return trade;
            }
        }
        return null;
    }

    /** Searches pending trades by user and returns the trades of the user
     * @param user the user whose pending trades are being searched
     * @return the trades of the yser
     */ //TradeManager
    public ArrayList<Trade> searchPendingTradesByUser(User user){
        ArrayList<Trade> userTrades = new ArrayList<Trade>();
        for(Trade trade : pendingTrades){
            if (trade.getUsername1().equals(user.getUsername()) || trade.getUsername2().equals(user.getUsername())){
                userTrades.add(trade);
            }
        }
        return userTrades;
    }

    /** Method which searches pending trades when given the trade's ID number and returns the trade.
     * Returns null if an invalid ID is given
     * @param tradeID ID number corresponding to the trade
     * @return the trade
     */ //TradeManager
    public Trade searchPendingTrade(int tradeID){
        for (Trade trade : pendingTrades){
            if (trade.getTradeID() == tradeID) {
                return trade;
            }
        }
        return null;
    }

    /** Method which ensures that neither user account is frozen.
     * Author: Louis Scheffer V
     * @param u1 user1
     * @param u2 user2
     * @return Boolean
     */ //TradeManager
    public Boolean beforeTrade(User u1, User u2){
        if (tradeHistories.getNumTradesThisWeek(u1.getUsername()) > completeThreshold ||
                tradeHistories.getNumTradesThisWeek(u2.getUsername()) > completeThreshold){
            return false;
        }
        return !(u1.getFrozen() || u1.getFrozen());
    }

    /** Method which allows a user to confirm a permanent trade has occurred.
     * @param user who is confirming the trade
     * @param trade the trade object
     */ //TradeManager????
    public void confirmTrade(UserManager userManager, User user, Trade trade){
        if(user.getUsername().equals(trade.getUsername1())){
            trade.setUser1TradeConfirmed(true);
        }
        else if (user.getUsername().equals(trade.getUsername2())){
            trade.setUser2AcceptedRequest(true);
        }
        if (trade.isTradeCompleted()){
            afterTrade(userManager, trade);
        }
    }

    /** Method which executes all item swaps and checks all pending trades and pending trade requests after a trade has
     * been completed. Trade is moved to completed trades if it is a
     * Author: Louis Scheffer V
     * @param trade trade object
     */ //TradeManager
    public void afterTrade(UserManager userManager, Trade trade){
        //TODO call this method from confirmTrade method in this class
        userManager.exchangeItems(trade);
        checkPendingTradeRequests(userManager);
        checkPendingTrades(userManager);
        pendingTrades.remove(trade);
        if (trade instanceof TemporaryTrade){
            tradeHistories.addCurrentTemporaryTrade((TemporaryTrade) trade);

        }
        else{
            tradeHistories.addCompletedTrade(trade);
        }

        User user1 = userManager.searchUser(trade.getUsername1());
        User user2 = userManager.searchUser(trade.getUsername2());

        assert user1 != null;
        //This might be > instead of >= idk lol
        if (user1.getNumBorrowed() + borrowLendThreshold > user1.getNumLent()){
            FreezeUserAlert alert = new FreezeUserAlert(user1.getUsername(),user1.getNumBorrowed(),
                    user1.getNumLent(), borrowLendThreshold);
            alertAdmin(alert);
        }
        assert user2 != null;
        if (user2.getNumBorrowed() + borrowLendThreshold > user2.getNumLent()){
            FreezeUserAlert alert = new FreezeUserAlert(user2.getUsername(), user2.getNumBorrowed(),
                    user2.getNumLent(), borrowLendThreshold);
            alertAdmin(alert);
        }
    }


    /** Method which checks all pending trades to see if the items are still available.If they are not then the trade
     * request is deleted.
     * Author: Louis Scheffer V
     */
    //TradeManager
    public void checkPendingTrades(UserManager userManager){
        for(Trade trade: pendingTrades){
            User user1 = userManager.searchUser(trade.getUsername1());
            User user2 = userManager.searchUser(trade.getUsername2());
            for(int itemID : trade.getItemIDsSentToUser1()){
                Item item = userManager.searchItem(user2, itemID);
                if (item == null){
                    pendingTrades.remove(trade);
                    UserAlert alert = new TradeCancelledAlert(trade.getTradeID());
                    alertUser(user1, alert);
                    alertUser(user2, alert);
                }
            }
            for(int itemID : trade.getItemIDsSentToUser2()) {
                Item item = userManager.searchItem(user1, itemID);
                if (item == null) {
                    pendingTrades.remove(trade);
                    UserAlert alert = new TradeCancelledAlert(trade.getTradeID());
                    alertUser(user1, alert);
                    alertUser(user2, alert);
                }
            }
        }
    }

    /** Method which checks all pending trades to see if the items are still available. If they are not then the trade
     * request is deleted.
     * Author: Louis Scheffer V
     */ //TradeManager
    public void checkPendingTradeRequests(UserManager userManager){
        for(Trade trade: pendingTradeRequests){
            User user1 = userManager.searchUser(trade.getUsername1());
            User user2 = userManager.searchUser(trade.getUsername2());
            for(int itemID : trade.getItemIDsSentToUser1()){
                Item item = userManager.searchItem(user2, itemID);
                if (item == null){
                    pendingTradeRequests.remove(trade);
                    UserAlert alert = new TradeRequestCancelledAlert(trade.getTradeID());
                    alertUser(user1, alert);
                    alertUser(user2, alert);
                }
            }
            for(int itemID : trade.getItemIDsSentToUser2()) {
                Item item = userManager.searchItem(user1, itemID);
                if (item == null) {
                    pendingTradeRequests.remove(trade);
                    UserAlert alert = new TradeRequestCancelledAlert(trade.getTradeID());
                    alertUser(user1, alert);
                    alertUser(user2, alert);
                }
            }
        }
    }

    /** Overloaded method to send an alert to a user. This one uses a user object.
     * Author: Louis Scheffer V
     * @param user object of the user who will be receiving the alert
     * @param alert alert object to send to the user
     */ //UserManager AND TradeManager
    public void alertUser(User user, UserAlert alert){
        String username = user.getUsername();
        alertUser(username, alert);
    }

    /** Overloaded method to send an alert to a user. This one uses a username.
     * Author: Louis Scheffer V
     * @param username username of the user receiving the alert
     * @param alert alert object to send to the user
     */ //UserManager AND TradeManager
    public void alertUser(String username, UserAlert alert){
        tradeHistories.alertUser(username, alert);
    }

    /** Method which sends an alert to the admin.
     *
     * @param a the AdminAlert being sent to the admin.
     */
    private void alertAdmin(AdminAlert a){
        this.adminAlerts.add(a);
    }

    public void setBorrowLendThreshold(int borrowLendThreshold){
        this.borrowLendThreshold = borrowLendThreshold;
    }

    public int getBorrowLendThreshold(){
        return this.borrowLendThreshold;
    }

    public void setCompleteThreshold(int completeThreshold){
        this.completeThreshold = completeThreshold;
    }

    public int getCompleteThreshold(){
        return this.completeThreshold;
    }
}
