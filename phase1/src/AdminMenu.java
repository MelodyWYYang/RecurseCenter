
import AlertPack.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {

    public void run() {
        boolean running = true;
        while (running) {
            int input = -1;
            Scanner scan = new Scanner(System.in);
            System.out.println("--- Admin Menu --- \n");
            System.out.println("(1) Set borrow/lend threshold \n\"(2) Add new admin" +
                    " \n(0) Quit");
            boolean valid_input = false;
            while (!valid_input) {
                input = scan.nextInt();
                if (input > 2 || input < 0) {
                    System.out.println("Please enter a number from 0 to 2");
                } else if (input == 1) {
                    AdminActions.setThreshold(TradeSystem.adminUser);
                    valid_input = true;
                } else if (input == 2) {
                    AdminActions.addNewAdmin(TradeSystem.adminUser);
                    valid_input = true;
                } else if (input == 0) {
                    valid_input = true;
                    running = false;
                }
            }
        }
    }



}


