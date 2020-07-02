import AlertPack.*;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminAlertManager {

    /** Method which iterates through each AdminAlert and handles it.
     *
     * @param alerts Array List of AdminAlerts that need to be processed.
     */
    public AdminUser adminUser = new AdminUser("admin", "admin");// Not really sure how we want to do this. Hardcoded for simplicity in the meanwhile - Louis
    //Todo replace above line with actual admin user.
    public void handleAlertQueue(ArrayList<AdminAlert> alerts){

        while(!(alerts.size() == 0)){
            AdminAlert alert = alerts.get(0);

        }

    }

    /** Method which sends each AdminAlert to the correct function to be handled based on its type
     *
     * @param a AdminAlert object to be handled
     */
    private void handleAlert(AdminAlert a){

        if (a instanceof ItemValidationRequestAlert){
                handleItemValidationRequestAlert((ItemValidationRequestAlert) a);
        } else if (a instanceof ReportAlert){
            handleReportAlert((ReportAlert) a);
        } else if (a instanceof FreezeUserAlert){
            handleFreezeUserAlert((FreezeUserAlert) a);
        }else if (a instanceof UnfreezeRequestAlert){
            handleUnfreezeRequestAlert((UnfreezeRequestAlert) a);
        }
    }

        //Each alert needs a handle method for its type, which prints/takes input and calls corresponding functions to
        //  handle the alert on the enduser side of things. See google doccument for specifics on alerts and their
        //  handling process.

    /**
     *
     * @param alert a
     */
    public void handleItemValidationRequestAlert(ItemValidationRequestAlert alert){
        Scanner scanner = new Scanner(System.in);
        System.out.println(alert.toString());
        System.out.println("(1) Approve this item");
        System.out.println("(2) Deny this item");
        int choice = optionChoice(2);
        adminUser.pollValidationRequest(choice == 1, alert);
    }

    public void handleReportAlert(ReportAlert alert){
        boolean flag = true;
        int input = 0;
        while (flag){
            Scanner scan = new Scanner(System.in);
            System.out.println(alert.toString());
            System.out.println("(1) Accept report");
            System.out.println("(2) Dismiss");
            input = scan.nextInt();
            if (input == 1){
                // increment incompleteTrades stat in AdminUser and freeze user if needed
                // incomplete trades are not being tracked currently. will update
                flag = false;
            }
            if (input == 2){
                flag = false;
            }
        }
    }

    private void handleFreezeUserAlert(FreezeUserAlert alert){
        // author: Callan Murphy
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Freeze User");
            System.out.println("(2) Dismiss");
            input = scan.nextInt();
            if (input == 1) {
                User user = UserManager.searchUser(alert.getUsername());
                adminUser.freezeUser(user);
                flag = false;
            }
            if (input == 2) flag = false;
        }
    }

    private void handleUnfreezeRequestAlert(UnfreezeRequestAlert alert){
        // author: Callan Murphy
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Unfreeze User");
            System.out.println("(2) Dismiss");
            input = scan.nextInt();
            if (input == 1) {
                User user = UserManager.searchUser(alert.getUsername());
                adminUser.unfreezeAccount(user);
                flag = false;
            }
            if (input == 2) flag = false;
        }
    }

    //helper method to ensure the user picks a valid choice, options are between 1 and x - Louis
    private int optionChoice(int x){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter one of the numbers listed above");
        int choice = scanner.nextInt();
        while(choice > x || choice <= 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 1 and " + x);
            choice = scanner.nextInt();
        }
        return choice;


    }
}


