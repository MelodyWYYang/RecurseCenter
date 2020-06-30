import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminUser implements Serializable {
    //author: Tingyu Liang, Riya Razdan in group 0110 for CSC207H1 summer 2020 project

    // private HashMap<String, String> loginInfo = new HashMap<String, String>();
    private String username;
    private String password;
    public static int incompleteThreshold; // # of incomplete trades allowed
    public static int completeThreshold; // # of complete trades allowed per week

    // public static ArrayList<ItemValidationRequest> itemValidationQueue;
    public static ArrayList<AdminUser> adminList;
    public ArrayList<String> accountsToFreezeQueue;
    public ArrayList<String> unfreezeRequestList; // list of accounts that have requested to be unfrozen
    public ArrayList<String> accountsToUnfreezeQueue; // list of accounts that satisfy permission to be unfrozen

    public AdminUser(String username, String password) {
        // this.loginInfo.put(username, password);
        // itemValidationQueue = new ArrayList<ItemValidationRequest>();
        this.username = username;
        this.password = password;
        accountsToFreezeQueue = new ArrayList<String>();
        unfreezeRequestList = new ArrayList<String>();
        accountsToUnfreezeQueue = new ArrayList<String>();
        adminList.add(this);
    }

    /**Temporarily abandon the function below by Tingyu
     * We are instantiating new AdminUser instead
    public void addLogin(String username, String password){
        this.loginInfo.put(username, password);
    }*/

    public void createAdmin(String username, String password) {
        AdminUser newAdmin = new AdminUser(username, password);
        newAdmin.accountsToFreezeQueue = this.accountsToFreezeQueue;
        newAdmin.accountsToUnfreezeQueue = this.accountsToUnfreezeQueue;
        newAdmin.unfreezeRequestList = this.unfreezeRequestList;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<AdminUser> getAdminList() {
        return adminList;
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

    /**Temporarily abandoned by Tingyu
    public HashMap<String, String> getLogInInfo(){
        return this.loginInfo;
    }

    Callan made the function below to search for username in case we are using hashmap again
     public String getUsername(){
        return (String) getLogInInfo().keySet().toArray()[0];
     }
     */

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
        if (user != null) { user.isFrozen(false);    // freeze User only when it is found
        }
        accountsToFreezeQueue.remove(0);
    }

    // The function below made by Tingyu, contact me if this is unnecessary or wrong
    public void rejectUnfreezeRequest(boolean accepted) {
        // from what i understand, this is rejecting the unfreeze request - riya
        String username = unfreezeRequestList.get(0);
        if (accepted) {
            User user = UserManager.searchUser(username);
            if (user != null) {
                user.isFrozen(true);
            }
        }
        unfreezeRequestList.remove(0);
    }

    public void moveToUnfreeze(ArrayList<String> unfreezeRequestList) {
        String username = unfreezeRequestList.get(0);
        if (username != null) {
            accountsToUnfreezeQueue.add(username);
            unfreezeRequestList.remove(0);
        }
    }

    public void dequeueAndUnfreeze(ArrayList<String> accountsToUnfreezeQueue){
        User user = UserManager.searchUser(accountsToUnfreezeQueue.get(0));
        if (user != null) {
            user.isFrozen(false);
        }
        accountsToUnfreezeQueue.remove(0);
    }

    public void changeThresholdForUser(int newThreshold) {
        Trade.numLendsForBorrowThreshold = newThreshold;
    }
}
