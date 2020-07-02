import AlertPack.FrozenAlert;
import AlertPack.ItemValidationDeclinedAlert;
import AlertPack.ItemValidationRequestAlert;
import AlertPack.UserAlert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminUser implements Serializable {
    //author: Tingyu Liang, Riya Razdan in group 0110 for CSC207H1 summer 2020 project

    // private HashMap<String, String> loginInfo = new HashMap<String, String>();
    private String username;
    private String password;
    public static int incompleteThreshold; // # of incomplete trades allowed
    public static int completeThreshold; // # of complete trades allowed per week

    private int borrowLendThreshold = 1;

    // public static ArrayList<ItemValidationRequest> itemValidationQueue;

    public ArrayList<String> accountsToFreezeQueue;
    public ArrayList<String> unfreezeRequestList; // list of accounts that have requested to be unfrozen
    public ArrayList<String> accountsToUnfreezeQueue; // list of accounts that satisfy permission to be unfrozen

    public UserManager userManager = new UserManager(); // Not really sure how we want to do this. Hardcoded for simplicity in the meanwhile - Louis
    //Todo replace above line with actual UserManager.

    public AdminUser(String username, String password) {
        // this.loginInfo.put(username, password);
        // itemValidationQueue = new ArrayList<ItemValidationRequest>();
        this.username = username;
        this.password = password;
        accountsToFreezeQueue = new ArrayList<String>();
        unfreezeRequestList = new ArrayList<String>();
        accountsToUnfreezeQueue = new ArrayList<String>();

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

    public void pollValidationRequest(boolean accepted, ItemValidationRequestAlert request) {
        if (accepted) {
            User user = UserManager.searchUser(request.getOwner());
            Item item = new Item(request.getName(), request.getItemID());
            item.setOwner(request.getOwner());
            item.setUserThatHasPossession(request.getOwner());
            item.setDescription(request.getDescription());
            assert user != null;
            user.availableItems.add(item);   //Changed this to fail when user is null. We don't want the program to
            // fail silently.
            // request.getOwner().availableItems.add(request.getObj());
        }
        else{
            System.out.println("Please enter a reason why this request was declined.");
            Scanner scanner = new Scanner(System.in);
            String message = scanner.next();
            UserAlert alert = new ItemValidationDeclinedAlert(request.getOwner(), request.getName(),
                    request.getDescription(), request.getItemID(), message);
            userManager.alertUser(request.getName(), alert);
        }
    }


    /** Method which freeze the user account and sends a FrozenAlert to the user
     * author: tian
     * @param user user object
     */
    public void freezeUser(User user){
        user.isFrozen(true);
        int numBorrowed = user.stats.get("Borrowed");
        int numLent = user.stats.get("Lent");
        FrozenAlert alert = new FrozenAlert(numBorrowed, numLent, numBorrowed - numLent);
        TradeSystem.userManager.alertUser(user.getUsername(), alert);
    }
    /*
    public void dequeueAndFreeze() {
        User user = UserManager.searchUser(accountsToFreezeQueue.get(0));
        if (user != null) { user.isFrozen(false);    // freeze User only when it is found
        }
        accountsToFreezeQueue.remove(0);
    }

     */
    /*
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

     */

    public void moveToUnfreeze(ArrayList<String> unfreezeRequestList) {
        String username = unfreezeRequestList.get(0);
        if (username != null) {
            accountsToUnfreezeQueue.add(username);
            unfreezeRequestList.remove(0);
        }
    }

    public void unfreezeAccount(User user){
        user.isFrozen(false);
    }

    public void changeThresholdForUser(int newThreshold) {
        Trade.numLendsForBorrowThreshold = newThreshold;
    }
}
