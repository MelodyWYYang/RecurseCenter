import java.io.Serializable;
import java.util.*;
import AlertPack.Alert;

public class User implements Serializable {
    //author: Melody Yang in group 0110 for CSC207H1 summer 2020 project
    // Sorting method orderPartners() is taken from https://howtodoinjava.com/sort/java-sort-map-by-values/

    public final String username;
    private String password; // private so no one can access except User; have setters and getters for change password function
    private int numLent;
    private int numBorrowed;
    private int numIncompleteTrades;
    private boolean frozen = false; // false being frozen or Lent>Borrowed; true being no violations
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

    /**
     *
     * @return the number of items they have borrowed.
     */
    public int getNumBorrowed() {
        return numBorrowed;
    }

    /**
     *
     * @return the number of incomplete trades that have been attributed to this user.
     */
    public int getNumIncompleteTrades() {
        return numIncompleteTrades;
    }

    /**
     *
     * @return the number of items that this user has lent.
     */
    public int getNumLent() {
        return numLent;
    }

    /**
     *
     * @param x the number to increase the number of items this user has borrowed by.
     */
    public void increaseNumBorrowed(int x){
        numBorrowed += x;
    }

    /**
     *
     * @param x the number to increase the number of items this user has lent by.
     */
    public void increaseNumLent(int x){
        numBorrowed += x;
    }

    /**
     *
     * @param x the number to increase the number of incomplete trades attributed to this user by.
     */
    public void increaseNumIncompleteTrades(int x){
        numIncompleteTrades += x;
    }

    /**
     *
     * @param password new password for this user.
     */
    public void setPassword(String password) { this.password = password; }// may want to extend a use case to change password if forgotten

    //do we want to have this?
    /**
     *
     * @return returns the password of this user.
     */
    public String getPassword() { return this.password;}

    /**
     *
     * @param pass string to be checked against this user's password.
     * @return whether the entered string matches this user's password.
     */
    public boolean checkPassword(String pass){return pass.equals(password);}

    //for adding and removing from wishlist and available-to-lend lists, and getters for this User's lists

    /**
     *
     * @return a list of the user's available items.
     */
    public ArrayList<Item> getAvailableItems() {return this.availableItems;}

    /**
     *
     * @return The wish list of the user.
     */
    public ArrayList<String> getWishlistItemNames() {return this.wishlistItemNames;}

    /**
     *
     * @param item item name to be added to this user's wish list.
     */
    public void addItemToWishList(String item){
        wishlistItemNames.add(item);
    }

    /**
     *
     * @param item item name to be removed from the user's wish list
     */
    public void removeItemFromWishList(String item){
        wishlistItemNames.remove(item);
    }

    /**
     *
     * @param item item to be added to the user's available items.
     */
    public void addAvailableItem(Item item){
        availableItems.add(item);
    }

    /**
     *
     * @param item item to be removed from the user's available items.
     */
    public void removeAvailableItem(Item item){
        availableItems.remove(item);
    }

    /**
     *
     * @param item item to be added to the user's borrowed items.
     */
    public void addBorrowedItem(Item item){
        borrowedItems.add(item);
    }

    /**
     *
     * @param item item to be removed from the user's borrowed items.
     */
    public void removeBorrowedItem(Item item){
        borrowedItems.remove(item);
    }

    /**
     *
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return whether or not a user is frozen.
     */
    public boolean getFrozen(){ // wondering how to implement freeze function with this; or should this only be a ThresholdChecker?
        // no code to automatically freeze because design says admin needs to do this
        return frozen;
    }

    /**
     *
     * @param frozen whether or not a user is frozen.
     */
    public void setFrozen(boolean frozen){
    this.frozen = frozen;
    } // whether the account is set to frozen or not

    /**
     *
     * @return string representation of the user.
     */
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
