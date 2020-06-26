import java.util.ArrayList;
import java.util.Optional;

public class AdminUser {
    //author: Tingyu Liang in group 0110 for CSC207H1 summer 2020 project

    private String username;
    private String password;
    public static int incompleteThreshold; // # of incomplete trades allowed

    public static ArrayList<ItemValidationRequest> itemValidationQueue;
    public static ArrayList<User> accountsToFreezeQueue;
    public static ArrayList<User> unfreezeRequestList; // list of accounts that have requested to be unfrozen
    public static ArrayList<User> accountsToUnfreezeQueue; // list of accounts that satisfy permission to be unfrozen

    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
        itemValidationQueue = new ArrayList<ItemValidationRequest>();
        accountsToFreezeQueue = new ArrayList<User>();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void pollValidationRequest(boolean accepted) {
        ItemValidationRequest request = itemValidationQueue.get(0);
        if (accepted) {
            request.getOwner().availableItems.add(request.getObj());
        }
        AdminUser.itemValidationQueue.remove(0);
    }


    public void dequeueAndFreeze(ArrayList<User> accountsToFreezeQueue) {
        User user = accountsToFreezeQueue.get(0);
        // requires implementation of the User class
        user.permission = false;
        accountsToFreezeQueue.remove(0);
    }

    public void moveToUnfreeze(ArrayList<User> unfreezeRequestList) {
        User user = unfreezeRequestList.get(0);
        if(user.changePermission()){

        };

    public void dequeueAndUnfreeze(ArrayList<User> accountsToUnfreezeQueue){
        User user = accountsToUnfreezeQueue.get(0);
        user.permission = true;

    }

    public void changeThresholdForUser(int newThreshold) {
        Trade.numLendsForBorrowThreshold = newThreshold;
    }
}
