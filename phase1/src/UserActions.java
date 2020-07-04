import AlertPack.AdminAlert;
import AlertPack.ReportAlert;

import javax.swing.text.View;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class UserActions {

    // hehe xd
    public AdminUser adminUser;
    public UserManager userManager = adminUser.userManager;
    //we can use this for now
    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public void run(User user){
        int input = 0;
        while (true){
            Scanner scan = new Scanner(System.in);
            System.out.println("--- User Menu --- \n");
            System.out.println("(1) View items and wishlist \n (2) View user stats \n (3) Request an unfreeze \n" +
                    "(4) View other users \n (5) View your pending trades \n (6) View active temporary trades");
            input = scan.nextInt();
            if (input >= 6 || input <= 1){
                System.out.println("Please enter a number from 1 to 6");
            } else break;
        }
        if (input == 1){
            viewItemAndWishlist(user);
        } else if (input == 2) {

        } else if (input == 3) {
            //KING TINGYU
        } else if (input == 4) {
            // KING MURRAY
        } else if (input == 5) {
            viewPendingTrades(user);
        } else if (input == 6) {
            // KING TINGYU
        }


    }

    public void viewItemAndWishlist(User user){
        boolean flag = true;
        int input = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Your available items:");
        for (Item item : user.getAvailableItems()){
            System.out.println(item);
        }
        System.out.println("\n Your wishlist :");
        for (Item item : user.getWishlistItems()){
            System.out.println(item);
        }

        while (flag){
            System.out.println("(1) Send Item Validation request \n (2) Remove an item from available items " +
                    "\n (3) Remove an item from your wishlist");
            input = scan.nextInt();
            if (input < 1 || input > 3){
                System.out.println("Please enter a number from 1 to 3");
            } else flag = false;

         if (input == 1){
             System.out.println("Please the name of your item");
             String name = scan.nextLine();
             System.out.println("Please the item description");
             String description = scan.next();
             System.out.println("Please your username");
             String username = scan.nextLine();
             TradeSystem.adminUser.userManager.sendValidationRequest(name,description,username);
         } else if (input == 2){
             System.out.println("Please the ID of the item you wish to remove from available items");
             int itemID = scan.nextInt();
             for (Item item: user.getAvailableItems()){
                 if (item.getId() == itemID) {
                     user.getAvailableItems().remove(item);
                 }
             }

         } else {
             System.out.println("Please the ID of the item you wish to remove from your wishlist");
             int itemID = scan.nextInt();
             for (Item item: user.getWishlistItems()){
                 if (item.getId() == itemID) {
                     user.getWishlistItems().remove(item);
                 }
             }
         }

        }

    }

    public void runStats(User user) {
        int input = 0;
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("--- View user stats ---");
            System.out.println("(1) View number of items user has borrowed \n (2) View number of items user has lent" +
                    "\n (3) View frozen status \n (4) View number of trades involving user that have not been" +
                    "completed \n (5) View number of transactions this week \n (6) View items recently traded away" +
                    "\n (7) View most frequent trading partners \n (8) Return to \"User Menu\"");
            input = scan.nextInt();
            if (input >= 8 || input <= 1) {
                System.out.println("Please enter a number from 1 to 8");
            } else break;
        }
        if (input == 1) {
            System.out.println("You have borrowed " + Integer.toString(user.getNumBorrowed()) + " items.");
            runStats(user);
        } else if (input == 2) {
            System.out.println("You have lent " + Integer.toString(user.getNumLent()) + " items.");
            runStats(user);
        } else if (input == 3) {
            if (user.getFrozen()) {
                System.out.println("Your account has been frozen");
            } else {
                System.out.println("Your account is not frozen");
            }
            runStats(user);
        } else if (input == 4) { //Other methods need access to UserManager methods

        } else if (input == 5) {

        } else if (input == 6) {

        } else if (input == 7) {

        } else if (input == 8) {
            run(user);
        }
    }
    public void viewPendingTrades(User user){
        int choice = 0;
        while (choice != 0 ) {
            ArrayList<Trade> userTrades = userManager.searchPendingTradesByUser(user);
            System.out.println("Options:");
            System.out.println("(1) Exit this menu");
            System.out.println("====================");
            System.out.println("Your pending trades:");
            for (Trade trade : userTrades) {
                System.out.println(userManager.tradeToString(trade));
            }
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            if (choice != 1){
                System.out.println("Your choice was not valid, please pick a valid choice.");
            }
        }
    }

}
