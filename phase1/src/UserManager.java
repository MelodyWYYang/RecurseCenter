import AlertPack.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserManager implements Serializable{
    //author: Jinyu Liu, Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    //All methods written by Jinyu Liu except where noted

    //UserManager
    protected ArrayList<User> listUsers = new ArrayList<User>(); // List of all users - Jinyu

    //TradeManager AND UserManager
    protected ArrayList<AdminAlert> adminAlerts = new ArrayList<AdminAlert>();

    //UserManager
    protected HashMap<String, ArrayList<UserAlert>> alertSystem = new HashMap<String, ArrayList<UserAlert>>();

    //UserManager -- all thresholds are admin things really, rethink this?
    private int incompleteThreshold; // # of incomplete trades allowed

    public void onStartUp(TradeCreator tradeCreator){
        HashMap<String, ArrayList<UserAlert>> alertsToAdd = tradeCreator.fetchUserAlerts();

        for (String key : alertsToAdd.keySet()){
            if (alertSystem.containsKey(key)){
                ArrayList<UserAlert> alertsForUser = alertSystem.get(key);
                alertsForUser.addAll(alertsToAdd.get(key));
                alertSystem.put(key, alertsForUser);
            } else {
                alertSystem.put(key, alertsToAdd.get(key));
            }
        }
    }

    //UserManager
    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    //TradeManager and UserManager
    public void clearAdminAlerts(){
        adminAlerts = new ArrayList<AdminAlert>();
    }

    //UserManager
    public ArrayList<UserAlert> getUserAlerts(String username){
        return alertSystem.get(username);
    }

    /**
     * Alerts the admin.
     * @param alert alert
     */ //TradeManager and UserManager
    public void alertAdmin(AdminAlert alert){
        this.adminAlerts.add(alert);
    }

    /** Method which exchanges the items in the trade system after a trade has been marked as completed
     * Author: Louis Scheffer V
     * @param trade trade object
     */ //TradeManager
    public void exchangeItems(Trade trade){
        User user1 = searchUser(trade.getUsername1());
        User user2 = searchUser(trade.getUsername2());
        for(int itemID : trade.getItemIDsSentToUser1()){
            Item item = searchItem(user2, itemID);
            //do borrowed and lent get incremented every trade or just during TemporaryTrades? - Louis
            user1.increaseNumBorrowed(1);
            user2.increaseNumLent(1);
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
            user2.increaseNumBorrowed(1);
            user1.increaseNumLent(1);
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
     * @param trade Temporary Trade Object
     */ //TradeManager???
    public void reExchangeItems(TemporaryTrade trade){
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


    /** 3-arg method which creates and instantiates an ItemvalidationRequest.
     * Author: Jinyu Liu
     * @param name name of the item
     * @param description description of the item
     * @param owner username of the user who will own the item
     * @return The ItemValidationRequestAlert in question.
     */ //TradeManager for cohesion reasons with alerting admin.
    public ItemValidationRequestAlert sendValidationRequest(String name, String description, String owner) {
        // reworked by Tingyu since the itemValidationRequestQueue has been moved to UserManager
        ItemValidationRequestAlert alert = new ItemValidationRequestAlert(owner, name, description);
        alertAdmin(alert);
        return alert;
    }

    /** Method which creates a user and adds it to the list of users
     * Author: Jinyu Liu
     * @param username username of user
     * @param password password of user
     * @return the created user
     */ //UserManager
    public User createUser(String username, String password) throws UserNameTakenException {
        System.out.println("Entered:" + username);
        for (User user : listUsers) {
            System.out.println("Iterated on " + user.getUsername());
            if (user.getUsername().equals(username)) {

                throw new UserNameTakenException("That username is taken.");
            }

        }
        User newUser = new User(username);
        newUser.setPassword(password);
        listUsers.add(newUser);
        alertSystem.put(username, new ArrayList<UserAlert>());
        return newUser;
    }

    /**
     * Adds an item to the user's wishlist
     * @param user user
     * @param itemName name of item
     */ //UserManager
    public void addToWishlist(User user, String itemName){
        user.addItemToWishList(itemName);
    }

    /**
     * removes an item from the user's wishlist
     * @param user user
     * @param itemName name of item
     */ //UserManager
    public void removeFromWishList(User user, String itemName){
        user.removeItemFromWishList(itemName);
    }


    /* No longer neccessary - Louis
    public void AddTransaction(Trade trade){
        transactions.add(trade);
    } // This is for adding completed transactions to the stored list - Mel
     */
    //UserManager //TODO: Move the unfreeze stuff from the controller/presenter layer to instead call this method.
    //public void requestUnfreeze(String username){
        // user can request to unfreeze account whether it should be unfrozen or not
        // moved by Riya from User
        // unfreezeRequestList.add(username);
        // UnfreezeRequestAlert alert = new UnfreezeRequestAlert(user1.getUsername(),user1.getNumBorrowed(),
        //                    user1.getNumLent(), borrowLendThreshold);
        //            adminAlerts.add(alert);
    //}

    /**
     * Return a list of all adminAlerts from this class. Also empties the adminAlerts member.
     * @return the list of adminAlerts
     */
    public ArrayList<AdminAlert> fetchAdminAlerts(){
        ArrayList<AdminAlert> alerts = this.adminAlerts;
        this.adminAlerts = new ArrayList<AdminAlert>();
        return alerts;
    }


//    public ArrayList<Item> RecentTransactionItems(User user) {
//        ArrayList<Trade> potentialRecentCompleted = new ArrayList<Trade>();
//        ArrayList<TemporaryTrade> potentialRecentIncompleted = new ArrayList<TemporaryTrade>();
//        ArrayList<Item> recents = new ArrayList<Item>();
//        for (Trade trade : completedTrades) {
//            if (trade.getUsername1().equals(user.username) & !trade.getItemIDsSentToUser2().isEmpty()) {
//                potentialRecentCompleted.add(trade);
//            } else if (trade.getUsername2().equals(user.username) & !trade.getItemIDsSentToUser1().isEmpty()) {
//                potentialRecentCompleted.add(trade);
//            }
//        }
//        for (TemporaryTrade tTrade: currentTemporaryTrades) {
//            if (tTrade.getUsername1().equals(user.username) & !tTrade.getItemIDsSentToUser2().isEmpty()) {
//                potentialRecentIncompleted.add(tTrade);
//            } else if (tTrade.getUsername2().equals(user.username) & !tTrade.getItemIDsSentToUser1().isEmpty()) {
//                potentialRecentIncompleted.add(tTrade);
//            }
//        }
//        while (recents.size() < 3 & (!potentialRecentCompleted.isEmpty() | !potentialRecentIncompleted.isEmpty())) {
//            if (!potentialRecentCompleted.isEmpty() & potentialRecentIncompleted.isEmpty()) {
//                Trade mostRecentComp = potentialRecentCompleted.get(potentialRecentCompleted.size() - 1);
//                if (mostRecentComp.getUsername1().equals(user.username)) {
//                    recents.add(searchItem(mostRecentComp.getItemIDsSentToUser2().get(0)));
//                    potentialRecentCompleted.remove(mostRecentComp);
//                } else if (mostRecentComp.getUsername2().equals(user.username)) {
//                    recents.add(searchItem(mostRecentComp.getItemIDsSentToUser1().get(0)));
//                    potentialRecentCompleted.remove(mostRecentComp);
//                }
//            } else if (!potentialRecentIncompleted.isEmpty() & potentialRecentCompleted.isEmpty()) {
//                Trade mostRecentIncomp = potentialRecentIncompleted.get(potentialRecentCompleted.size() - 1);
//                if (mostRecentIncomp.getUsername1().equals(user.username)) {
//                    recents.add(searchItem(mostRecentIncomp.getItemIDsSentToUser2().get(0)));
//                    potentialRecentIncompleted.remove(mostRecentIncomp);
//                } else if (mostRecentIncomp.getUsername2().equals(user.username)) {
//                    recents.add(searchItem(mostRecentIncomp.getItemIDsSentToUser1().get(0)));
//                    potentialRecentIncompleted.remove(mostRecentIncomp);
//                }
//            } else {
//                Trade mostRecentComp = potentialRecentCompleted.get(potentialRecentCompleted.size() - 1);
//                Trade mostRecentIncomp = potentialRecentIncompleted.get(potentialRecentIncompleted.size() - 1);
//                if (mostRecentComp.getTimeOfTrade().isAfter(mostRecentIncomp.getTimeOfTrade())) {
//                    if (mostRecentComp.getUsername1().equals(user.username)) {
//                        recents.add(searchItem(mostRecentComp.getItemIDsSentToUser2().get(0)));
//                        potentialRecentCompleted.remove(mostRecentComp);
//                    } else if (mostRecentComp.getUsername2().equals(user.username)) {
//                        recents.add(searchItem(mostRecentComp.getItemIDsSentToUser1().get(0)));
//                        potentialRecentCompleted.remove(mostRecentComp);
//                    }
//                } else if (mostRecentIncomp.getTimeOfTrade().isAfter(mostRecentComp.getTimeOfTrade())) {
//                    if (mostRecentIncomp.getUsername1().equals(user.username)) {
//                        recents.add(searchItem(mostRecentIncomp.getItemIDsSentToUser2().get(0)));
//                        potentialRecentIncompleted.remove(mostRecentIncomp);
//                    } else if (mostRecentIncomp.getUsername2().equals(user.username)) {
//                        recents.add(searchItem(mostRecentIncomp.getItemIDsSentToUser1().get(0)));
//                        potentialRecentIncompleted.remove(mostRecentIncomp);
//                    }
//                }
//            }
//        }
//        return recents;
//    }
//As ugly as this is, please don't delete this yet until I am sure the newer code works - Jinyu

    /** Method which returns a user when given their username
     * Author: Louis Scheffer V
     * @param username username of the user
     * @return user object
     */ //UserManager
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
     */ //Wherever is most convenient
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
     *///Ditto from above
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
        ArrayList<UserAlert> alerts = alertSystem.get(username);
        alerts.add(alert);
        alertSystem.put(username, alerts);
    }

    /** Method which allows a user to send a message to another user, using the alert system.
     * Author: Louis Scheffer V
     * @param sender user object who is sending the message.
     * @param recipient user object who is receiving the message.
     * @param message message text.
     */ //UserManager
    public void sendMessageToUser(User sender, User recipient, String message){
        UserAlert alert = new MessageAlert(sender.getUsername(), message);
        alertUser(recipient, alert);
    }

    /**
     *
     * @return the threshold of lent - borrowed which all users should be at or above.
     */
    public int getIncompleteThreshold() {
        return incompleteThreshold;
    }

    /**
     *
     * @param incompleteThreshold the threshold of lent - borrowed which all users should be at or above.
     */
    public void setIncompleteThreshold(int incompleteThreshold) {
        this.incompleteThreshold = incompleteThreshold;
    }

    /**
     *
     * @param user the number by which to increase the number of incomplete trades attributed to a user by.
     */
    public void increaseUserIncompleteTrades(User user){
        user.increaseNumIncompleteTrades(1);
    }

    /**
     *
     * @param user the user which is the number of incomplete trades is being queried.
     * @return the number of incomplete trades attributed to the user.
     */
    public int getUserIncompleteTrades(User user){
        return user.getNumIncompleteTrades();
    }
}