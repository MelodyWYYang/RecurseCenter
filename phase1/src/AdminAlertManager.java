import AlertPack.*;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminAlertManager { //This class has a two way dependency with TradeSystem

    /** Method which iterates through each AdminAlert and handles it.
     *
     * @param alerts Array List of AdminAlerts that need to be processed.
     */
    public void handleAlertQueue(MenuPresenter menuPresenter, AdminUser adminUser, UserManager userManager, TradeCreator tradeCreator,
                                 ArrayList<AdminAlert> alerts){

        while(!(alerts.size() == 0)){
            AdminAlert alert = alerts.get(0);
            handleAlert(menuPresenter, adminUser, userManager, tradeCreator, alert);
            alerts.remove(0);
        }

    }

    /** Method which sends each AdminAlert to the correct function to be handled based on its type
     *
     * @param a AdminAlert object to be handled
     */
    private void handleAlert(MenuPresenter menuPresenter, AdminUser adminUser, UserManager userManager, TradeCreator tradeCreator, AdminAlert a){
        if (a instanceof ItemValidationRequestAlert){
                handleItemValidationRequestAlert(adminUser, userManager, (ItemValidationRequestAlert) a);
        } else if (a instanceof ReportAlert){
            handleReportAlert(adminUser, userManager, tradeCreator, (ReportAlert) a);
        } else if (a instanceof FreezeUserAlert){
            handleFreezeUserAlert(adminUser, userManager,(FreezeUserAlert) a);
        }else if (a instanceof UnfreezeRequestAlert){
            handleUnfreezeRequestAlert(userManager, adminUser, (UnfreezeRequestAlert) a);
        }
    }

        //Each alert needs a handle method for its type, which prints/takes input and calls corresponding functions to
        //  handle the alert on the enduser side of things. See google doccument for specifics on alerts and their
        //  handling process.

    /** Method that handles an ItemValidationRequestAlert by approving or denying the request
     *
     * @param alert AdminAlert that there is an ItemValidationRequestAlert to be handled
     */
    private void handleItemValidationRequestAlert(AdminUser adminUser, UserManager userManager, ItemValidationRequestAlert alert){
        System.out.println("Item validation request\nUser: " + alert.getOwner() + "\nItem name: " + alert.getName() +
                "\nItem description: " + alert.getDescription() + "\nItem ID number: " + alert.getItemID());
        Scanner scanner = new Scanner(System.in);
        String message;
        System.out.println("(1) Approve this item");
        System.out.println("(2) Deny this item");
        int choice = optionChoice(2);
        if (choice == 2){
            System.out.println("Please enter a reason why this request was declined.");
            message = scanner.next();
        }else{
            message = "";
        }
        adminUser.pollValidationRequest(userManager,choice == 1, alert, message);
    }

    /** Method that handles a ReportAlert by accepting the report or dismissing it
     *
     * @param alert AdminAlert that there is a ReportAlert to be handled
     */
    private void handleReportAlert(AdminUser adminUser, UserManager userManager, TradeCreator tradeCreator,
                                   ReportAlert alert){
        System.out.println(alert.getSenderUserName() + " has reported user " + alert.getReportedUserName() +
                " whose trade status is " + alert.getIsTradeComplete()
                + "\n" + "Details: " + alert.getReportDescription());
        boolean flag = true;
        int input = 0;
        int numIncompTrades = 0;
        int threshold = 0; // threshold of incomplete trades
        while (flag){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Accept report");
            System.out.println("(2) Dismiss");
            input = scan.nextInt();
            if (input == 1){
                userManager.increaseUserIncompleteTrades(userManager.searchUser(alert.getReportedUserName()));
                int numIncompleteTrades = tradeCreator.getNumIncompTrades(alert.getReportedUserName());
                threshold = userManager.getIncompleteThreshold();
                if (numIncompleteTrades > threshold){
                    User reportedUser = userManager.searchUser(alert.getReportedUserName());
                    adminUser.freezeUser(userManager, reportedUser);
                }
                flag = false;
            }
            if (input == 2){
                flag = false;
            }
        }
    }

    /** Method that handles a FreezeUserAlert by freezing the user or dismissing the alert
     *
     * @param alert AdminAlert that there is a user that should be frozen
     */
    private void handleFreezeUserAlert(AdminUser adminUser, UserManager userManager, FreezeUserAlert alert){

        System.out.println("Freeze User Alert" +
                "\n" + alert.getUsername() + " has lent: " + alert.getLent() + " items" +
                "\n" + alert.getUsername() + " has borrowed: " + alert.getBorrowed() + " items" +
                "\n" + "Required to lend " + alert.getThresholdRequired() + " more items than borrowed");
        // author: Callan Murphy
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Freeze User");
            System.out.println("(2) Dismiss");
            input = scan.nextInt();
            if (input == 1) {
                User user = userManager.searchUser(alert.getUsername());
                assert user != null;
                adminUser.freezeUser(userManager, user);
                flag = false;
            }
            if (input == 2) flag = false;
        }
    }

    /** Method that handles a UnfreezeUserRequestAlert by unfreezing the user that requested the unfreeze of dismissing
     * the alert
     *
     * @param alert AdminAlert that there is a user who has requested that their account be unfrozen
     */
    private void handleUnfreezeRequestAlert(UserManager userManager, AdminUser adminUser, UnfreezeRequestAlert alert){

        System.out.println("Unfreeze User Request Alert" +
                "\n" + alert.getUsername() + " has lent: " + alert.getLent() + " items" +
                "\n" + alert.getUsername() + " has borrowed: " + alert.getBorrowed() + " items" +
                "\n" + "Required to lend " + alert.getThresholdRequired() + " more items than borrowed");
        // author: Callan Murphy
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Unfreeze User");
            System.out.println("(2) Dismiss");
            input = scan.nextInt();
            if (input == 1) {
                User user = userManager.searchUser(alert.getUsername());
                adminUser.unfreezeAccount(user);
                flag = false;
            }
            if (input == 2) flag = false;
        }
    }

    /** Helper method that checks user input to ensure that they made a valid choice (options are between 1 and x)
     *
     * @param x user can input any value from 1 to x (inclusive)
     * @return int
     */
    //helper method to ensure the user picks a valid choice, options are between 1 and x - Louis
    private int optionChoice(int x){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter one of the numbers listed above");
        int choice = scanner.nextInt();
        while(choice >= x || choice < 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 1 and " + x);
            choice = scanner.nextInt();
        }
        return choice;


    }
}


