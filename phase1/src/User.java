import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class User {
    //author: Melody Yang in group 0110 for CSC207H1 summer 2020 project
    //User constructor
    public User(String username) {
        stats.put("Lent", 0); // # lent items since creation
        stats.put("Borrowed", 0); // # borrowed items since creation
        stats.put("weeklyT", 0); //// # completed transactions in this week; AdminUser can access;
        // Use Cases need to increase after each 1-way or 2-way trade; and reset each week
        stats.put("incompleteT", 0); // # incomplete transactions since creation
        this.username = username; // Admin needs to access to freeze; USerManager needs to access/search by User
    }

    public String username;
    private String password; // private so no one can access except User; have setters and getters for change password function

    protected LinkedHashMap<String, Integer> stats = new LinkedHashMap<String, Integer>(); //LinkedHashMap maintains order, so always index-able
    protected HashMap<String, Integer> partners = new HashMap<String, Integer>(); // list of all Users this User has traded with since creation

    public boolean permission = false; // false being frozen or Lent>Borrowed; true being no violations

    public ArrayList<Item> availableItems; // if this was protected then our presenters can't access it
    public ArrayList<Item> wishlistItems; // presenter needs to access this as well

    public void setPassword(String password) { this.password = password; }// may want to extend a use case to change password if forgotten

    //for adding and removing from wishlist and available-to-lend lists, and getters for this User's lists
    public ArrayList<Item> getAvailableItems() {return this.availableItems;}
    public ArrayList<Item> getWishlistItems() {return this.wishlistItems;}
    public void addItemToList(Item a, ArrayList<Item> list) {list.add(a);}
    public void removeItemFromList(Item a, ArrayList<Item> list) {list.remove(a);}

    //for changing #items Borrowed and Lent by this User
    public void increaseStat(String stat, int num) {
        // input BorL is Borrow or Lent
        switch (stat.toLowerCase()) {
            case "borrowed": {
                int old = stats.get("Borrowed");
                stats.put("Borrowed", (old + num));
                 // need to check Lent>More -- make helper
                break;
            }
            case "lent": {
                int old = stats.get("Lent");
                stats.put("Lent", (old + num));
                break;
            }
            case "weeklyt": {
                int old = stats.get("weeklyT");
                stats.put("weeklyT", (old + num));
                break;
            }
            case "incompletet": {
                int old = stats.get("incompleteT");
                stats.put("incompleteT", (old + num));
                break;
            }
            default:
                break;
        }
    }

    public void checkPermission(){ // wondering how to implement freeze function with this; or should this only be a ThresholdChecker?
        // no code to automatically freeze because design says admin needs to do this
        permission = stats.get("incompleteT") < AdminUser.incompleteThreshold && stats.get("Lent") >
                stats.get("Borrowed") + Trade.numLendsForBorrowThreshold;
    }


    public void addPartner(String username2){
        if (partners.containsKey(username2)){
            int old = partners.get(username2);
            partners.put(username2, old + 1);}
        else{partners.put(username2, 0);}
    }

    public ArrayList<String> Favourites(){
        TreeMap<String, Integer> top3 = new LinkedHashMap<String, Integer>();
        //sort partners hashmap by values, append each into top 3
        for partners.
        partners.entrySet()
    } // top 3 trading partners, access partners hashmap and find highest 3 trades(value) completed and return the 3 keys


}