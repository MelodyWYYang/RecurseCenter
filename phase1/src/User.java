import java.io.Serializable;
import java.util.*;
import AlertPack.Alert;

public class User implements Serializable {
    //author: Melody Yang in group 0110 for CSC207H1 summer 2020 project
    // Sorting method orderPartners() is taken from https://howtodoinjava.com/sort/java-sort-map-by-values/

    public final String username;
    private String password; // private so no one can access except User; have setters and getters for change password function
    protected LinkedHashMap<String, Integer> stats = new LinkedHashMap<String, Integer>(); //LinkedHashMap maintains order, so always index-able
    private int numLent;
    private int numBorrowed;
    private int numIncompleteTrades;
    public boolean frozen = false; // false being frozen or Lent>Borrowed; true being no violations
    private ArrayList<Item> availableItems = new ArrayList<Item>(); // if this was protected then our presenters can't access it
    private ArrayList<Item> borrowedItems = new ArrayList<Item>();// items that the user is currently borrowing via TemporaryTrade - Louis
    private ArrayList<String> wishlistItemNames = new ArrayList<String>();// presenter needs to access this as well


    //User constructor
    public User(String username) {
        numBorrowed = 0;
        numIncompleteTrades = 0;
        numLent = 0;
        //stats.put("Lent", 0); // # lent items since creation
        //stats.put("Borrowed", 0); // # borrowed items since creation
        // Use Cases need to increase after each 1-way or 2-way trade; and reset each week
        //stats.put("incompleteT", 0); // # incomplete transactions since creation
        this.username = username; // Admin needs to access to freeze; USerManager needs to access/search by User
    }


    public int getNumBorrowed() {
        return numBorrowed;
    }

    public int getNumIncompleteTrades() {
        return numIncompleteTrades;
    }

    public int getNumLent() {
        return numLent;
    }

    public void increaseNumBorrowed(int x){
        numBorrowed += x;
    }

    public void increaseNumLent(int x){
        numBorrowed += x;
    }

    public void increaseNumIncompleteTrades(int x){
        numIncompleteTrades += x;
    }

    public void setPassword(String password) { this.password = password; }// may want to extend a use case to change password if forgotten
    public String getPassword() { return this.password;}
    public boolean checkPassword(String pass){return pass.equals(password);}

    //for adding and removing from wishlist and available-to-lend lists, and getters for this User's lists
    public ArrayList<Item> getAvailableItems() {return this.availableItems;}
    public ArrayList<String> getWishlistItemNames() {return this.wishlistItemNames;}


    public void addItemToWishList(String item){
        wishlistItemNames.add(item);
    }
    public void removeItemFromWishList(String item){
        wishlistItemNames.remove(item);
    }
    public void addAvailableItem(Item item){
        availableItems.add(item);
    }
    public void removeAvailableItem(Item item){
        availableItems.remove(item);
    }
    public void addBorrowedItem(Item item){
        borrowedItems.add(item);
    }
    public void removeBorrowedItem(Item item){
        borrowedItems.remove(item);
    }

    //I needed this for UserManager - Louis
    public String getUsername() {
        return username;
    }

    public boolean getFrozen(){ // wondering how to implement freeze function with this; or should this only be a ThresholdChecker?
        // no code to automatically freeze because design says admin needs to do this
        return frozen;
    }

    public void setFrozen(boolean frozen){
    this.frozen = frozen;
    } // whether the account is set to frozen or not


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
