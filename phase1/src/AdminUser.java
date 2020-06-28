import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class AdminUser implements Serializable {
    //author: Tingyu Liang, Riya Razdan in group 0110 for CSC207H1 summer 2020 project

    private String username;
    private String password;
    public static int incompleteThreshold; // # of incomplete trades allowed
    public static int completeThreshold; // # of complete trades allowed per week

    // public static ArrayList<ItemValidationRequest> itemValidationQueue;
    public static ArrayList<String> accountsToFreezeQueue;
    public static ArrayList<String> unfreezeRequestList; // list of accounts that have requested to be unfrozen
    public static ArrayList<String> accountsToUnfreezeQueue; // list of accounts that satisfy permission to be unfrozen

    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
        // itemValidationQueue = new ArrayList<ItemValidationRequest>();
        accountsToFreezeQueue = new ArrayList<String>();
        unfreezeRequestList = new ArrayList<String>();
        accountsToUnfreezeQueue = new ArrayList<String>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void setIncompleteThreshold(int incompleteThreshold) {
        AdminUser.incompleteThreshold = incompleteThreshold;
    }

    public static void setCompleteThreshold(int completeThreshold) {
        AdminUser.completeThreshold = completeThreshold;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void pollValidationRequest(boolean accepted) {
        ItemValidationRequest request = UserManager.itemValidationRequestQueue.get(0);
        if (accepted) {
            User user = UserManager.searchUser(request.usernameOfOwner);
            Item item = new Item(request.name, request.itemID);
            item.setOwner(request.usernameOfOwner);
            item.setUserThatHasPossession(request.usernameOfOwner);
            item.setDescription(request.description);
            if (user != null) {
                user.addItemToList(item, user.availableItems);    // add item only when User is found
            }
            // request.getOwner().availableItems.add(request.getObj());
        }
        UserManager.itemValidationRequestQueue.remove(0);
    }


    public void dequeueAndFreeze() {
        User user = UserManager.searchUser(accountsToFreezeQueue.get(0));
        if (user != null) {
            user.setFrozen(false);    // freeze User only when it is found
        }
        accountsToFreezeQueue.remove(0);
    }

    public void moveToUnfreeze(ArrayList<String> unfreezeRequestList) {
        String user = unfreezeRequestList.get(0);
        if (!user.gblahblahblah im done with this{
            accountsToUnfreezeQueue.add(user);
            unfreezeRequestList.remove(0);
        }
    }

    public void dequeueAndUnfreeze(ArrayList<String> accountsToUnfreezeQueue){
        String user = accountsToUnfreezeQueue.get(0);
        user.checkPermission() = true;
        accountsToUnfreezeQueue.remove(0);

    }

    public void changeThresholdForUser(int newThreshold) {
        Trade.numLendsForBorrowThreshold = newThreshold;
    }
}
