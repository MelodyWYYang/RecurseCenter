import AlertPack.AdminAlert;
import AlertPack.ExpirationAlert;
import AlertPack.UserAlert;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TradeHistories  implements Serializable {
    protected ArrayList<TemporaryTrade> currentTemporaryTrades = new ArrayList<TemporaryTrade>(); //list of all temporary trades where items have
    // been exchanged but not returned -Louis

    protected ArrayList<Trade> completedTrades = new ArrayList<Trade>(); // list of all trades which have been completed - Louis

    protected ArrayList<AdminAlert> adminAlerts = new ArrayList<AdminAlert>();

    protected HashMap<String, ArrayList<UserAlert>> userAlertsToDispatch = new HashMap<String, ArrayList<UserAlert>>();
    /**
     * Return a list of all adminAlerts stored in this class. Also empties the adminAlerts member.
     * @return an ArrayList of admin alerts
     */
    public ArrayList<AdminAlert> fetchAdminAlerts() {
        ArrayList<AdminAlert> alerts = this.adminAlerts;
        this.adminAlerts = new ArrayList<AdminAlert>();
        return alerts;
    }

    public HashMap<String, ArrayList<UserAlert>> fetchUserAlerts(){
        HashMap<String, ArrayList<UserAlert>> alerts = userAlertsToDispatch;
        this.userAlertsToDispatch = new HashMap<String, ArrayList<UserAlert>>();
        return alerts;
    }

    public void alertUser(User user, UserAlert alert){
        String username = user.getUsername();
        alertUser(username, alert);
    }

    public void alertUser(String username, UserAlert alert){
        ArrayList<UserAlert> userAlerts;
        if (userAlertsToDispatch.containsKey(username)) {
            userAlerts = userAlertsToDispatch.get(username);
        } else{
            userAlerts = new ArrayList<UserAlert>();
        }
        userAlerts.add(alert);
        userAlertsToDispatch.put(username, userAlerts);
    }

    /**
     *
     * @param tempTrade the temporary trade being added to the list of completed trades.
     */
    public void addCurrentTemporaryTrade(TemporaryTrade tempTrade){
        this.currentTemporaryTrades.add(tempTrade);
    }

    /**
     *
     * @param trade the trade being added to the list of completed trades.
     */
    public void addCompletedTrade(Trade trade){
        this.completedTrades.add(trade);
    }

    /** Helper function that returns a list of all the trades that user participated in and traded an item. The list is
     * order by the date that the item left the user's possession.
     * @param username Username of User being evaluated
     * @return ArrayList</Trade> (sorted by LocalTimeDate)
     */ //TradeManager, This uses User objects so be careful about splitting.
    private ArrayList<Trade> getOrderedTrades(String username) {
        ArrayList<Trade> completed = new ArrayList<Trade>();
        ArrayList<TemporaryTrade> incompleted = new ArrayList<TemporaryTrade>();
        ArrayList<Trade> recent = new ArrayList<Trade>();

        for (Trade trade : completedTrades) {
            if (trade.getUsername1().equals(username) & !trade.getItemIDsSentToUser2().isEmpty()) {
                completed.add(trade);
            } else if (trade.getUsername2().equals(username) & !trade.getItemIDsSentToUser1().isEmpty()) {
                completed.add(trade);
            }
        }

        for (TemporaryTrade tTrade : currentTemporaryTrades) {
            if (tTrade.getUsername1().equals(username) & !tTrade.getItemIDsSentToUser2().isEmpty()) {
                incompleted.add(tTrade);
            } else if (tTrade.getUsername2().equals(username) & !tTrade.getItemIDsSentToUser1().isEmpty()) {
                incompleted.add(tTrade);
            }
        }

        while (!completed.isEmpty() | !incompleted.isEmpty()) {
            if (!completed.isEmpty() & incompleted.isEmpty()) {
                recent.addAll(completed);
                completed.clear();
            } else if (completed.isEmpty() & !incompleted.isEmpty()) {
                recent.addAll(incompleted);
                incompleted.clear();
            } else {
                if (completed.get(0).getTimeOfTrade().isBefore(incompleted.get(0).getTimeOfTrade())) {
                    recent.add(completed.get(0));
                    completed.remove(0);
                } else {
                    recent.add(incompleted.get(0));
                    incompleted.remove(0);
                }
            }
        }
        return recent;
    }


    /** Helper function that returns an ordered list of all items' ID that the user traded away. The list is ordered by
     * the date that the user traded the item away.
     * @param username username of User being evaluated
     * @return ArrayList</int> (sorted by LocalTimeDate)
     */ //TradeManager
    private ArrayList<Integer> getOrderedItemsID(String username) {
        ArrayList<Trade> tradeHistory = this.getOrderedTrades(username);
        ArrayList<Integer> orderedItemsID = new ArrayList<Integer>();
        for (Trade trade : tradeHistory) {
            if (trade.getUsername1().equals(username)) {
                orderedItemsID.addAll(trade.getItemIDsSentToUser2());
            } else if (trade.getUsername2().equals(username)) {
                orderedItemsID.addAll(trade.getItemIDsSentToUser1());
            }
        }
        return orderedItemsID;
    }

    /** Returns an ordered list of all items that the user traded away. The list is ordered by the date that the user
     * traded the item away.
     * @param user User being evaluated
     * @param n number of items
     * @return ArrayList</Item> (sorted by LocalTimeDate)
     */ //TradeManager
    public ArrayList<Item> getNRecentItems(UserManager userManager, String user, int n) {
        ArrayList<Integer> orderedItemsID = this.getOrderedItemsID(user);
        //TODO: This might not work
        ArrayList<Integer> orderedItemsIDClone = (ArrayList<Integer>) orderedItemsID.clone();
        ArrayList<Item> nOrderedItems = new ArrayList<Item>();
        while (nOrderedItems.size() < n & !orderedItemsIDClone.isEmpty()) {
            nOrderedItems.add(userManager.searchItem(orderedItemsIDClone.get(orderedItemsIDClone.size() - 1)));
            orderedItemsIDClone.remove(orderedItemsIDClone.size() - 1);
        }
        return nOrderedItems;
    }

    /**
     *
     * @return the date & time which the current week starts.
     */
    private LocalDateTime getStartofWeek() {
        LocalDateTime timeNow = LocalDateTime.now(); //gets the current time
        LocalDateTime timeNowBeginning = timeNow.withHour(0).withMinute(0).withSecond(0).withNano(0); //set time 00:00
        return timeNowBeginning.minusDays(timeNowBeginning.getDayOfWeek().getValue());
    }

    /** Number of trades carried out by the user in a week
     * @param username username of User whose number of trades is being calculated
     * @return the number of transactions in a week
     */ //TradeManager
    public int getNumTradesThisWeek(String username) {
        int numTransactions = 0;
        LocalDateTime startOfWeek = this.getStartofWeek();
        for (Trade trade : completedTrades) {
            if (trade.getUsername1().equals(username) & trade.getTimeOfTrade().isAfter(startOfWeek)) {
                numTransactions++;
            } else if (trade.getUsername2().equals(username) & trade.getTimeOfTrade().isAfter(startOfWeek)) {
                numTransactions++;
            }
        }
        for (TemporaryTrade tTrade: currentTemporaryTrades) {
            if (tTrade.getUsername1().equals(username) & tTrade.getTimeOfTrade().isAfter(startOfWeek)) {
                numTransactions++;
            } else if (tTrade.getUsername2().equals(username) & tTrade.getTimeOfTrade().isAfter(startOfWeek)) {
                numTransactions++;
            }
        }
        return numTransactions;
    }

    private HashMap<String, Integer> getNumTradesPerUser(String username) {
        HashMap<String, Integer> numTradesPerUser = new HashMap<String, Integer>();
        for (Trade trade: completedTrades) {
            if (trade.getUsername1().equals(username)) {
                String partnerUsername = trade.getUsername2();
                numTradesPerUser.putIfAbsent(trade.getUsername2(), 0);
                numTradesPerUser.replace(trade.getUsername2(), numTradesPerUser.get(trade.getUsername2()) + 1);
            } else if (trade.getUsername2().equals(username)) {
                String partnerUsername = trade.getUsername1();
                numTradesPerUser.putIfAbsent(trade.getUsername1(), 0);
                numTradesPerUser.replace(trade.getUsername1(), numTradesPerUser.get(trade.getUsername1()) + 1);
            }
        }
        return numTradesPerUser;
    }

    /** Top trading partners for a user.
     * @param n number of top trading partners to be considered
     * @param username name of User being evaluated
     * @return the usernames of the top trading partners for a given user
     */ //TradeManager
    public ArrayList<String> getTopNTradingPartners(String username, int n) {
        HashMap<String, Integer> numTradesPerUser = this.getNumTradesPerUser(username);
        HashMap<String, Integer> numTradesPerUserClone = (HashMap<String, Integer>) numTradesPerUser.clone();
        ArrayList<String> topPartnersUsername = new ArrayList<String>();
        while (topPartnersUsername.size() < n & !numTradesPerUserClone.isEmpty()) {
            int maxInt = 0;
            StringBuilder favouritePartner = new StringBuilder();
            for (Map.Entry<String, Integer> mapping : numTradesPerUserClone.entrySet()) {
                if (mapping.getValue() > maxInt) {
                    maxInt = (mapping.getValue());
                    StringBuilder string = new StringBuilder();
                    favouritePartner = string.append(mapping.getKey());
                    //Code fragment based off of code in https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
                }
            }
            topPartnersUsername.add(favouritePartner.toString());
            numTradesPerUserClone.remove(favouritePartner.toString());
        }
        return topPartnersUsername;
    }


    /** Method which returns a temporary trade when given its ID number.
     * Returns null if an invalid ID is given
     * @param tradeID ID number corresponding to the trade
     * @return the temporary trade
     */ //TradeManager
    public TemporaryTrade searchTemporaryTrade(int tradeID) {
        for (TemporaryTrade tempTrade : currentTemporaryTrades){
            if (tempTrade.getTradeID() == tradeID){
                return tempTrade;
            }
        }
        return null;
    }

    /** Searches current temporary trades by user and returns the trades of the user
     * @param user the user whose trades are being searched
     * @return the trades of the user
     */ //TradeManager
    public ArrayList<TemporaryTrade> searchActiveTempTradesByUser(User user) {
        ArrayList<TemporaryTrade> userTrades = new ArrayList<TemporaryTrade>();
        for (TemporaryTrade trade: currentTemporaryTrades) {
            if (trade.getUsername1().equals(user.getUsername()) || trade.getUsername2().equals(user.getUsername())) {
                userTrades.add(trade);
            }
        }
        return userTrades;
    }


    /**
     * Check to see if any TemporaryTrades have expired and if so, add an alert to the User's alertQueue.
     * Author: Murray Smith
     * Rework by Louis Scheffer V 6/30/20 // modifications made to work with the alert system.
     * Rework by Tian Yue Dong 7/1/2020 ; made it so it only sends alert to user who didn't confirm the reexchange
     */ //TradeManager
    public void checkForExpiredTempTrades(){
        for (TemporaryTrade tempTrade : currentTemporaryTrades) {
            if (LocalDateTime.now().isAfter(tempTrade.getDueDate())) {
                LocalDateTime dueDate = tempTrade.getDueDate();
                int tradeID = tempTrade.getTradeID();

                if (!tempTrade.getUser1ItemReturnRequestAccepted()){
                    UserAlert alert = new ExpirationAlert(dueDate,tempTrade.getUsername1(), tradeID);
                    alertUser(tempTrade.getUsername1(), alert);
                }
                if (!tempTrade.getUser2ItemReturnRequestAccepted()){
                    UserAlert alert = new ExpirationAlert(dueDate, tempTrade.getUsername2(), tradeID);
                    alertUser(tempTrade.getUsername2(), alert);
                }

            }
        }
    }

    /** Method which allows a user to confirm the re-exchange of items has occurred in the real world. If the other
     * user has already confirmed, then the reExchangeItems method will be called to reExahange the items within the
     * trade system.
     * Author: Louis Scheffer V
     * @param user user who is confirming the re-exchange of items.
     * @param temporaryTrade the temporary trade object.
     */ //TradeManager????
    public void confirmReExchange(UserManager userManager, User user, TemporaryTrade temporaryTrade){
        if(user.getUsername().equals(temporaryTrade.getUsername1())){
            temporaryTrade.setUser1ItemReturnRequestAccepted(true);
        }
        else if (user.getUsername().equals(temporaryTrade.getUsername2())){
            temporaryTrade.setUser2ItemReturnRequestAccepted(true);
        }
        if (temporaryTrade.getUser1ItemReturnRequestAccepted() && temporaryTrade.getUser2ItemReturnRequestAccepted()){
            userManager.reExchangeItems(temporaryTrade);
        }
    }
}
