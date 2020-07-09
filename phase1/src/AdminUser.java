import AlertPack.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminUser implements Serializable {
    //author: Tingyu Liang, Riya Razdan in group 0110 for CSC207H1 summer 2020 project

    private HashMap<String, String> loginInfo;


    private ArrayList<AdminAlert> adminAlerts;

     // Not really sure how we want to do this. Hardcoded for simplicity in the meanwhile - Louis

    /**
     * Constructor for AdminUser
     * @param username string for the admin's username
     * @param password string for the admin's password
     */
    public AdminUser(String username, String password) {
        adminAlerts = new ArrayList<AdminAlert>();
        loginInfo = new HashMap<String, String>();
        addLogin(username, password);

    }

    public boolean isValidUsername(String username){
        return loginInfo.containsKey(username);
    }


    public void addLogin(String username, String password){
        this.loginInfo.put(username, password);
    }


    public ArrayList<AdminAlert> getAdminAlerts() {
        return adminAlerts;
    }


    public void changeIncompleteThreshold(UserManager userManager, int incompleteThreshold) {
        userManager.setIncompleteThreshold(incompleteThreshold);
    }

    public void changeCompleteThreshold(TradeCreator tradeCreator, int completeThreshold) {
        tradeCreator.setCompleteThreshold(completeThreshold);
    }

    /**
     * Manages all startup information for the admin
     */
    public void onStartUp(UserManager userManager, TradeCreator tradeCreator){
        this.adminAlerts = userManager.fetchAdminAlerts();
        this.adminAlerts.addAll(tradeCreator.fetchAdminAlerts());
        userManager.clearAdminAlerts();
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

    /**
     * Determines acceptance of item validation request and either creates/adds the new item,
     * or provides an alert that it has been declined
     * @param accepted boolean for whether or not the request has been accepted
     * @param request corresponding ItemValidationRequestAlert object
     * @param message validation request message
     */
    public void pollValidationRequest(UserManager userManager,
                                      boolean accepted, ItemValidationRequestAlert request, String message) {
        if (accepted) {
            User user = userManager.searchUser(request.getOwner());
            Item item = new Item(request.getName(), request.getItemID());
            item.setDescription(request.getDescription());
            assert user != null;
            user.addAvailableItem(item);   //Changed this to fail when user is null. We don't want the program
            // to fail silently.
            // request.getOwner().availableItems.add(request.getObj());
        }
        else{
            UserAlert alert = new ItemValidationDeclinedAlert(request.getOwner(), request.getName(),
                    request.getDescription(), request.getItemID(), message);
            userManager.alertUser(request.getName(), alert);
        }
    }


    /** Method which freezes the user account and sends a FrozenAlert to the user
     * author: tian
     * @param user user object to freeze
     */
    public void freezeUser(UserManager userManager, User user){
        user.setFrozen(true);
        int numBorrowed = user.getNumBorrowed();
        int numLent = user.getNumLent();
        FrozenAlert alert = new FrozenAlert(numBorrowed, numLent, numBorrowed - numLent);
        userManager.alertUser(user.getUsername(), alert);
    }

    /**
     * Unfreezes user account
     * @param user account to unfreeze
     */
    public void unfreezeAccount(User user){
        user.setFrozen(false);
    }

    /**
     * Change the borrow/lend threshold value
     * @param newThreshold int variable for new threshold
     */
    public void changeThresholdForUser(TradeCreator tradeCreator, int newThreshold) {
        tradeCreator.setBorrowLendThreshold(newThreshold);
    }


    /**
     * Checks if password is correct
     * @param pass string of password attempt to check
     * @return whether or not password is correct
     */
    public boolean checkPassword(String username, String pass) {
        return loginInfo.get(username).equals(pass);
    }
}
