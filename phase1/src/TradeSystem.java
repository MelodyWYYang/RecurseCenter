import AlertPack.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeSystem {

    public static AdminUser adminUser = new AdminUser("admin", "admin");

    static UserAlertManager userAlertManager = new UserAlertManager();

    static AdminAlertManager adminAlertManager = new AdminAlertManager();
    static AdminActions adminActions = new AdminActions();
    static UserActions userActions = new UserActions();


    public static void main(String[] args){
        //TODO: Create the adminUser.ser file if it does not exist.
        adminUser = FileManager.loadAdminUser("adminUser.ser");

        onStartUp();

        Scanner scan = new Scanner(System.in);
        User loggedIn = null;
        boolean isAdmin = false;
        System.out.println("Welcome to Insert_name_here trading system!\n Would you like to create an account or " +
                "login?\n(1) Create account \n(2)Login to existing account\n(0) Quit");
        int input = optionChoice(2);
        //TODO: Should we make this stuff its own class?
        if (input == 1){
            createAccount();
        } else if (input == 2){
            User x = login();
            if (x == null){
                isAdmin = true;
            } else {
                loggedIn = x;
            }

        } else if(input == 0){
            return;
        }

        if (isAdmin){
            ArrayList<AdminAlert> adminAlerts = adminUser.getAdminAlerts();
            adminAlertManager.handleAlertQueue(adminAlerts);
            adminActions.run();
        } else {
            ArrayList<UserAlert> userAlerts = adminUser.userManager.getAlerts(loggedIn.getUsername());
            userAlertManager.handleAlertQueue(userAlerts);
            userActions.run(loggedIn);
        }

        FileManager.saveAdminToFile(adminUser);
    }

    private static void onStartUp(){
        adminUser.onStartUp();
        adminUser.userManager.onStartUp();
    }

    private static void createAccount(){

    }

    private static User login(){
        return null;
    }

    private static int optionChoice(int x){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter one of the numbers listed above");
        int choice = scanner.nextInt();
        while(choice >= x || choice <= 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 0 and " + x);
            choice = scanner.nextInt();
        }
        return choice;


    }
}

