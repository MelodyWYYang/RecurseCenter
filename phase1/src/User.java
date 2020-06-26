import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class User {
    //author: Melody Yang in group 0110 for CSC207H1 summer 2020 project

    //User constructor
    public User(String username) {
        stats.put("Lent", 0); // # lent items since creation
        stats.put("Borrowed", 0); // # borrowed items since creation
        stats.put("weeklyT", 0); //// # completed transactions in this week; AdminUser can access;
        // Use Cases need to increase after each 1-way or 2-way trade; and reset each week
        stats.put("incompleteT", 0); // # incomplete transactions since creation
        username = username; // Admin needs to access to freeze; USerManager needs to access/search by User
    }

    private String password; // private so no one can access except User; have setters and getters for change password function

    protected LinkedHashMap<String, Integer> stats = new LinkedHashMap<String, Integer>(); //LinkedHashMap maintains order, so always index-able
    protected ArrayList<Trade> transactions; // list of all transactions this User has completed since creation
    protected HashMap<User, Integer> partners = new HashMap<User, Integer>(); // list of all Users this User has traded with since creation

    public boolean permission = false; // false being frozen or Lent>Borrowed; true being no violations

    public ArrayList<Item> availableItems; // if this was protected then our presenters can't access it
    public ArrayList<Item> wishlistItems; // presenter needs to access this as well
    protected ArrayList<Trade> requestQueue; // this stays protected because only the User needs it

    //A stack of trade requests. Queue at the end and dequeue at index 0.
    private ArrayList<Trade> tradeRequestQueue;

    public void setPassword(String password) { this.password = password; }// may want to extend a use case to change password if forgotten

    //for adding and removing from wishlist and available-to-lend lists, and getters for this User's lists
    public ArrayList<Item> getAvailableItems() {return this.availableItems;}
    public ArrayList<Item> getWishlistItems() {return this.wishlistItems;}
    public void addItemToList(Item a, ArrayList<Item> list) {list.add(a);}
    public void removeItemFromList(Item a, ArrayList<Item> list) {list.remove(a);}

    //for changing #items Borrowed and Lent by this User
    public void increaseStat(String stat) {
        // input BorL is Borrow or Lent
        switch (stat.toLowerCase()) {
            case "borrowed": {
                int old = stats.get("Borrowed");
                stats.put("Borrowed", (old + 1));
                System.out.println("Successfully added to Borrowed statistics."); // need to check Lent>More -- make helper

                break;
            }
            case "lent": {
                int old = stats.get("Lent");
                stats.put("Lent", (old + 1));
                System.out.println("Successfully added to Lent statistics.");
                break;
            }
            case "weeklyt": {
                int old = stats.get("weeklyT");
                stats.put("weeklyT", (old + 1));
                System.out.println("Successfully added to weeklyT statistics.");
                break;
            }
            case "incompletet": {
                int old = stats.get("incompleteT");
                stats.put("incompleteT", (old + 1));
                System.out.println("Successfully added to incompleteT statistics.");
                break;
            }
            default:
                System.out.println("Please pass either 'borrowed', 'lent', 'weeklyt', or 'incompletet'.");
                break;
        }
    }


    public void changePermission(){ // wondering how to implement freeze function with this; or should this only be a ThresholdChecker?
        if (stats.get("Lent") > stats.get("Borrowed") + Trade.numLendsForBorrowThreshold){
            permission = true;} // no code to automatically freeze because design says admin needs to do this
        else {
            permission = false;}
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
    }
        // most recent 3 transactions, access transactions list and take last 3
    // code for case where User hasn't traded w 3 ppl yet

    public void addPartner(User user2){
        if (partners.containsKey(user2)){
            int old = partners.get(user2);
            partners.put(user2, old + 1);}
        else{partners.put(user2, 0);}
    }

//    public User Favourites(){
//        partners.put()
//    } // top 3 trading partners, access partners hashmap and find highest 3 trades(value) completed and return the 3 keys


    public Trade dequeueTradeRequest(){
        return this.tradeRequestQueue.remove(0);
    }
}
