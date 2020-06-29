import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    //author: Melody Yang in group 0110 for CSC207H1 summer 2020 project
    // Sorting method orderPartners() is taken from https://howtodoinjava.com/sort/java-sort-map-by-values/

    //User constructor
    public User(String username) {
        stats.put("Lent", 0); // # lent items since creation
        stats.put("Borrowed", 0); // # borrowed items since creation
        // Use Cases need to increase after each 1-way or 2-way trade; and reset each week
        stats.put("incompleteT", 0); // # incomplete transactions since creation
        this.username = username; // Admin needs to access to freeze; USerManager needs to access/search by User
    }

    public final String username;
    private String password; // private so no one can access except User; have setters and getters for change password function

    protected LinkedHashMap<String, Integer> stats = new LinkedHashMap<String, Integer>(); //LinkedHashMap maintains order, so always index-able
    protected HashMap<String, Integer> partners = new HashMap<String, Integer>(); // list of all Users this User has traded with since creation
    protected LinkedHashMap<String, Integer> orderedPartners = new LinkedHashMap<String, Integer>();

    public boolean frozen = false; // false being frozen or Lent>Borrowed; true being no violations

    public ArrayList<Item> availableItems; // if this was protected then our presenters can't access it
    public ArrayList<Item> borrowedItems; // items that the user is currently borrowing via TemporaryTrade - Louis
    public ArrayList<Item> wishlistItems; // presenter needs to access this as well

    public ArrayList<String> alertQueue;

    public void setPassword(String password) { this.password = password; }// may want to extend a use case to change password if forgotten
    public String getPassword(String password) { return this.password;}

    //for adding and removing from wishlist and available-to-lend lists, and getters for this User's lists
    public ArrayList<Item> getAvailableItems() {return this.availableItems;}
    public ArrayList<Item> getWishlistItems() {return this.wishlistItems;}

    /**Method to add an item to one of the AvailableItems, WishlistItems, or borrowedItems lists,
     * and remove it from the other 2 lists.
     * @param a </Item>
     * Author: Melody Yang
     */
    public void addItemToList(Item a, ArrayList<Item> list) {
        list.add(a);
    }

    private void searchAndRemoveItem(Item item){

    }

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

            case "incompletet": {
                int old = stats.get("incompleteT");
                stats.put("incompleteT", (old + num));
                break;
            }
            default:
                break;
        }
    }

    public boolean getFrozen(){ // wondering how to implement freeze function with this; or should this only be a ThresholdChecker?
        // no code to automatically freeze because design says admin needs to do this
        return frozen;
    }

    public void setFrozen(boolean frozen){
    this.frozen = frozen;
    }


    public void requestUnfreeze(User user){ // user can request to unfreeze account whether it should be unfrozen or not

    }

    public void addPartner(String username2){
        if (partners.containsKey(username2)){
            int old = partners.get(username2);
            partners.put(username2, old + 1);}
        else{partners.put(username2, 0);}
        this.orderPartners();
    }

    private void orderPartners(){
        //sort partners hashmap by values in descending order, append each into LinkedHashMap orderedPartners
        partners.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(lambda -> orderedPartners.put(lambda.getKey(), lambda.getValue()));
    }
    //orderPartners is called after every time a addPartner is called in order to update the top 3.

    public List favourites() {
        List top3 = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            top3.add(i, orderedPartners.get(i)); }
        return top3;
    }
}
    // top 3 trading partners, access orderedPartners LinkedHashMap and return first three username Strings.
    // this needs to be updated after every transaction.

    //I added these getters and setters for use in UserManager - Louis --> these were already made under different names,
    // I've modified your UserManager code so they work for you. :) - Melody
