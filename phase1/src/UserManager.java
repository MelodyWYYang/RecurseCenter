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

    //UserManager
    public ArrayList<User> getListUsers() {
        return listUsers;}

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

    //TradeManager and UserManager
    public ArrayList<AdminAlert> getAdminAlerts(){
        return this.adminAlerts;
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

    public int getIncompleteThreshold() {
        return incompleteThreshold;
    }

    public void setIncompleteThreshold(int incompleteThreshold) {
        this.incompleteThreshold = incompleteThreshold;
    }

    public void increaseUserIncompleteTrades(User user){
        user.increaseNumIncompleteTrades(1);
    }

    public int getUserIncompleteTrades(User user){
        return user.getNumIncompleteTrades();
    }
}