import java.io.Serializable;
import java.util.*;
import AlertPack.Alert;

public class User implements Serializable {
    //author: Melody Yang in group 0110 for CSC207H1 summer 2020 project
    // Sorting method orderPartners() is taken from https://howtodoinjava.com/sort/java-sort-map-by-values/

    public final String username;
    private String password; // private so no one can access except User; have setters and getters for change password function

    protected LinkedHashMap<String, Integer> stats = new LinkedHashMap<String, Integer>(); //LinkedHashMap maintains order, so always index-able
    protected HashMap<String, Integer> partners = new HashMap<String, Integer>(); // list of all Users this User has traded with since creation
    protected LinkedHashMap<String, Integer> orderedPartners = new LinkedHashMap<String, Integer>();

    public boolean frozen = false; // false being frozen or Lent>Borrowed; true being no violations

    public ArrayList<Item> availableItems = new ArrayList<Item>(); // if this was protected then our presenters can't access it
    public ArrayList<Item> borrowedItems = new ArrayList<Item>();// items that the user is currently borrowing via TemporaryTrade - Louis
    public ArrayList<String> wishlistItemNames = new ArrayList<String>();// presenter needs to access this as well

    public ArrayList<Alert> alertQueue = new ArrayList<Alert>();

    //User constructor
    public User(String username) {
        stats.put("Lent", 0); // # lent items since creation
        stats.put("Borrowed", 0); // # borrowed items since creation
        // Use Cases need to increase after each 1-way or 2-way trade; and reset each week
        stats.put("incompleteT", 0); // # incomplete transactions since creation
        this.username = username; // Admin needs to access to freeze; USerManager needs to access/search by User
    }


    public int getNumBorrowed() {
        return this.stats.get("Borrowed");


    }
    public int getNumLent() {
        return this.stats.get("Lent");
    }


    public void setPassword(String password) { this.password = password; }// may want to extend a use case to change password if forgotten
    public String getPassword() { return this.password;}
    public boolean checkPassword(String pass){return pass.equals(password);}

    //for adding and removing from wishlist and available-to-lend lists, and getters for this User's lists
    public ArrayList<Item> getAvailableItems() {return this.availableItems;}
    public ArrayList<String> getWishlistItemNames() {return this.wishlistItemNames;}

    //TODO: Make an AddtoList method for each list. Also make a remove method for each.
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
    //TODO: Fix this.
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
    //I needed this for UserManager - Louis
    public String getUsername() {
        return username;
    }

    public void setFrozen(boolean frozen){
    this.frozen = frozen;
    } // whether the account is set to frozen or not


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

    public String toString(){
        StringBuilder userString = new StringBuilder("User: " + username + "\n");
        if (availableItems.size() == 0){
            userString.append("This User has no items available for trade. \n");
        } else {
            userString.append("Items available for trade: \n");
            for (int i = 0; i < availableItems.size() - 1; i++) {
                userString.append(availableItems.get(i).getName() + " (ID: " + availableItems.get(i).getId() + "), ");
            }
            userString.append(availableItems.get(availableItems.size() - 1) + " (ID: " +
                    availableItems.get(availableItems.size() - 1).getId() + ")\n");
        }
        if (wishlistItemNames.size() == 0){
            userString.append("This User has no items in their wishlist. \n");
        } else {
            userString.append("Wishlist: \n");
            for (int i = 0; i < wishlistItemNames.size() - 1; i++) {
                userString.append(wishlistItemNames.get(i) + ", ");
            }
            userString.append(wishlistItemNames.get(wishlistItemNames.size() - 1) + "\n");
        }
        if (getFrozen()){
            userString.append("This user is frozen, and thus cannot make a trade. \n");
        }

        return userString.toString();
    }
}
    // top 3 trading partners, access orderedPartners LinkedHashMap and return first three username Strings.
    // this needs to be updated after every transaction.

    //I added these getters and setters for use in UserManager - Louis --> these were already made under different names,
    // I've modified your UserManager code so they work for you. :) - Melody
