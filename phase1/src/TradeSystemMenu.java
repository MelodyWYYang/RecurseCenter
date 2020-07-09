import AlertPack.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeSystemMenu {
// TODO: call the run method
// Text file time
    public void run() {
        if (!((new File("adminUser.ser"))).exists()) {
            TradeSystem.createAdminUser();
        }

        TradeSystem.adminUser = FileManager.loadAdminUser("adminUser.ser");

        TradeSystem.onStartUp();

        Scanner scan = new Scanner(System.in);
        User loggedIn = null;
        boolean isAdmin = false;

        System.out.println("Welcome to Insert_name_here trading system!\n Would you like to create an account or " +
                "login?\n(1) Create account \n(2) Login to an account\n(0) Quit");

        int input = TradeSystem.optionChoice(2);
        //TODO: This will log a user in a admin if an incorrect username and password is entered.
        if (input == 1) {
            loggedIn = TradeSystem.createAccount();
        } else if (input == 2) {
            User x = TradeSystem.login();
            if (x == null) {
                isAdmin = true;
            } else {
                loggedIn = x;
            }

        } else if (input == 0) {
            return;
        }

        if (isAdmin) {
            if (TradeSystem.adminUser.getAdminAlerts().size() > 0) {
                System.out.println("Admin has alerts");
            }
            ArrayList<AdminAlert> adminAlerts = TradeSystem.adminUser.getAdminAlerts();
            TradeSystem.adminAlertManager.handleAlertQueue(adminAlerts);
            //TODO: Ensure the alert queue is depleted after all are handled.
            AdminMenu.run();
        } else {
            ArrayList<UserAlert> userAlerts = TradeSystem.userManager.getUserAlerts(loggedIn.getUsername());
            TradeSystem.userAlertManager.handleAlertQueue(userAlerts);
            TradeSystem.userActions.run(loggedIn);
        }

        FileManager.saveAdminToFile(TradeSystem.adminUser);


    }
}
