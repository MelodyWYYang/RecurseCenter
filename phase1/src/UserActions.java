import AlertPack.AdminAlert;
import AlertPack.ReportAlert;
import AlertPack.UnfreezeRequestAlert;

import javax.swing.text.View;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: Make all these methods public.
public class UserActions {


    public void run(User user){
        mainMenu(user);
    }

    public void mainMenu(User user){

        boolean running = true;
        while (running) {
            int input = -1;
            Scanner scan = new Scanner(System.in);
            System.out.println("--- User Menu --- \n");
            System.out.println("(1) View items and wishlist \n(2) View user stats \n(3) Request an unfreeze \n" +
                    "(4) View other users \n(5) View your pending trades \n(6) View active temporary trades \n" +
                    "(0) Quit");
            boolean valid_input = false;
            while(!valid_input){
                System.out.println("Please enter a number corresponding to a setting above:\n");
                input = scan.nextInt();
                if (input > 6 || input < 0) {
                    System.out.println("Please enter a number from 1 to 6");
                } else if (input == 1) {
                    viewItemAndWishlist(user);
                    valid_input = true;
                } else if (input == 2) {
                    runStats(user);
                    valid_input = true;
                } else if (input == 3) {
                    sendUnfreezeRequest(user);
                    valid_input = true;
                } else if (input == 4) {
                    viewAllUsers(user);
                    valid_input = true;
                } else if (input == 5) {
                    viewPendingTrades(user);
                    valid_input = true;
                } else if (input == 6) {
                    viewActiveTempTrades(user);
                    valid_input = true;
                } else if (input == 0){
                    valid_input = true;
                    running = false;
                }
        }
        }
    }

    public void viewItemAndWishlist(User user){
        boolean flag = true;
        int input = 0;
        Scanner scan = new Scanner(System.in);
        if (user.getAvailableItems().size() == 0){
            System.out.println("You have no items.");
        } else {
            System.out.println("Your available items:");
            for (Item item : user.getAvailableItems()) {
                System.out.println(item);
            }
        }
        System.out.println("\n Your wishlist :");
        for (String itemName : user.getWishlistItemNames()){
            System.out.println(itemName);
        }

        while (flag){
            System.out.println("(1) Send Item Validation request \n (2) Remove an item from available items " +
                    "\n (3) Remove an item from your wishlist");
            input = scan.nextInt();
            if (input < 1 || input > 3){
                System.out.println("Please enter a number from 1 to 3");
            } else flag = false;

         if (input == 1){
             System.out.println("Please enter the name of your item");
             String name = scan.nextLine();
             System.out.println("Please enter the item description");
             String description = scan.next();
             System.out.println("Please enter your username");
             String username = scan.nextLine();
             TradeSystem.adminUser.userManager.sendValidationRequest(name,description,username);
         } else if (input == 2){
             System.out.println("Please enter the ID of the item you wish to remove");
             int itemID = scan.nextInt();
             for (Item item: user.getAvailableItems()){
                 if (item.getId() == itemID) {
                     user.getAvailableItems().remove(item);
                 }
             }

         } else {
             System.out.println("Please the ID of the item you wish to remove from your wishlist");
             String inputItemName = scan.nextLine();
             for (String itemName: user.getWishlistItemNames()){
                 if (itemName.equals(inputItemName)) {
                     //TODO: ensure this alias works
                     user.getWishlistItemNames().remove(itemName);
                 }
             }
         }

        }

    }

    public void viewAllUsers(User userViewing){
        boolean handled = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("--- View other users ---");
        System.out.println("Enter a number to view a User's page:");
        int page = 1;
        ArrayList<User> allUsers = TradeSystem.adminUser.userManager.getListUsers();
        boolean nextPageExists = true;
        while (!handled){
            int input = -1;
            StringBuilder usersString = new StringBuilder();
            for(int i = (9 * (page - 1)) + 1; i < (9 * page) + 1; i++){
                try {
                    usersString.append("(").append(i).append(") ").append(allUsers.get(i - 1).getUsername()).append("\n");
                } catch (IndexOutOfBoundsException e){
                    nextPageExists = false;
                    usersString.append("Back to Main Menu");
                    break;
                }
            }
            if (nextPageExists) {
                usersString.append("(0) next page (current page: ").append(page).append(")").append("\n");
            }
            System.out.println(usersString.toString());
            input = scan.nextInt();
            boolean valid_input = false;
            while(!valid_input) {
                if (input < 0 || input > allUsers.size() + 1 || (!nextPageExists && input == 0)){
                    System.out.println("Please Enter a valid input.");
                    input = scan.nextInt();
                } else if (input == allUsers.size() + 1){
                    handled = true;
                    valid_input = true;
                }
                else if (input == 0) {
                    page += 1;
                    valid_input = true;
                } else {
                    handled = true;
                    valid_input = true;
                    System.out.println("Viewing User: " + allUsers.get(input - 1).getUsername() + "...");
                    viewUser(allUsers.get(input - 1), userViewing);
                }
            }
        }
    }

    private void viewUser(User userToView, User userViewing) {
        Scanner scan = new Scanner(System.in);
        StringBuilder userString = new StringBuilder(userToView.toString());
        userString.append("(1) Send a message\n");
        userString.append("(2) Add one of their items to your wishlist\n");
        if (!userToView.getFrozen()) {
            userString.append("(3) Send a trade request\n");
        }
        userString.append("(0) Back to Main Menu");
        boolean handled = false;
        int input;
        while(!handled){
            System.out.println(userString.toString());

            input = scan.nextInt();

            if (input < 0 || input > 3 || (userToView.getFrozen() && input == 3)){
                System.out.println("Please enter a valid input");
            } else if (input == 1) {
                System.out.println("Enter the contents of your message:\n");
                String message = scan.nextLine();
                TradeSystem.adminUser.userManager.sendMessageToUser(userViewing, userToView, message);
                System.out.println("Sent message to " + userToView.getUsername() + ": \"" + message + "\"");
            } else if (input == 2){
                System.out.println("Enter the name of the item you would like added to your wishlist:\n");
                String itemString = scan.nextLine();
                TradeSystem.adminUser.userManager.addToWishlist(userViewing, itemString);
            } else if (input == 3 && !userToView.getFrozen()){
                formTradeRequest(userViewing, userToView);
            } else if (input == 0){
                handled = true;

            }
        }
    }

    private void formTradeRequest(User userSending, User userReceiving) {
        //TODO: Possibly have a limit to the number of items that can be traded at once?
        Scanner scan = new Scanner(System.in);
        boolean finished = false;
        ArrayList<Integer> itemIDsRecieved = new ArrayList<Integer>();
        if (userReceiving.getAvailableItems().size() == 0){
            System.out.println("The other user has no available items. You will not receive anything in this trade.");
            finished = true;
        }
        while (!finished) {
            System.out.println("Enter the ID of an item want from this trade:\n");
            int ID = scan.nextInt();
            boolean validID = false;
            for (Item item : userReceiving.getAvailableItems()) {
                if (item.getId() == ID) {
                    itemIDsRecieved.add(ID);
                    validID = true;
                }
            }
            if (!validID) {
                System.out.println("Invalid ID. Please try again.\n");
            } else {
                boolean validYNInput = false;
                while (!validYNInput)
                    System.out.println("Would you like to add annother item? (Y/N)");
                String annotherItem = scan.nextLine();
                if (annotherItem.equals("Y")) {
                    validYNInput = true;
                } else if (annotherItem.equals("N")) {
                    System.out.println("Moving on...");
                    validYNInput = true;
                    finished = true;
                } else {
                    System.out.println("Please enter Y or N.");
                }
            }
        }
        ArrayList<Integer> itemIDsSent = new ArrayList<Integer>();

        boolean finished2 = false;
        if (userSending.getAvailableItems().size() == 0){
            System.out.println("You will not be offering anything with this trade.");
            finished2 = true;
        }
        while (!finished2) {
            System.out.println("Enter the ID of an item you want to offer:\n");
            System.out.println("Your available items to trade:\n");
            StringBuilder availableItems = new StringBuilder();
            for (int i = 0; i < userSending.getAvailableItems().size() - 1; i++) {
                availableItems.append(userSending.getAvailableItems().get(i).getName() +
                        " (ID: " + userSending.getAvailableItems().get(i).getId() + "), ");
            }
            availableItems.append(userSending.getAvailableItems().get(userSending.getAvailableItems().size() -
                    1).getName() + " (ID: " +
                    userSending.getAvailableItems().get(userSending.getAvailableItems().size() - 1).getId() + ") ");
            System.out.println(availableItems);

            int ID2 = scan.nextInt();
            boolean validID2 = false;
            for (Item item : userSending.getAvailableItems()) {
                if (item.getId() == ID2) {
                    itemIDsSent.add(ID2);
                    validID2 = true;
                }
            }
            if (!validID2) {
                System.out.println("Invalid ID. Please try again.\n");
            } else {
                boolean validYNInput2 = false;
                while (!validYNInput2)
                    System.out.println("Would you like to add another item? (Y/N)");
                String annotherItem = scan.nextLine();
                if (annotherItem.equals("Y")) {
                    validYNInput2 = true;
                } else if (annotherItem.equals("N")) {
                    System.out.println("Moving on...");
                    validYNInput2 = true;
                    finished2 = true;
                } else {
                    System.out.println("Please enter Y or N.");
                }
            }
        }

        System.out.println("Enter a meeting time (format: yyyy-MM-dd HH:mm: \n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        boolean stringNotFound = true;
        LocalDateTime meetingTime = null;
        while (stringNotFound) {

            String inputDateTime = scan.nextLine();
            try {
                meetingTime = LocalDateTime.parse(inputDateTime, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format for Date and Time, Try again (format: yyyy-MM-dd HH:mm):\n");
                continue;
            }
            stringNotFound = false;
        }

        System.out.println("Enter a meeting place:\n");
        String meetingPlace = scan.nextLine();

        TradeSystem.adminUser.userManager.sendTradeRequest(userSending, userReceiving, itemIDsRecieved, itemIDsSent,
                meetingTime, meetingPlace);

        System.out.println("Successfully created and sent trade request. You will be notified when they respond.");
    }


    public void runStats(User user) {
        int input = 0;
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("--- View user stats ---");
            System.out.println("(1) View number of items user has borrowed \n(2) View number of items user has lent" +
                    "\n(3) View frozen status \n(4) View number of trades involving user that have not been" +
                    "completed \n(5) View number of transactions this week \n(6) View items recently traded away" +
                    "\n(7) View most frequent trading partners \n(8) Return to \"User Menu\"");
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

    public void sendUnfreezeRequest(User user) {
        if (!user.getFrozen()) {
            System.out.println("Your account is not frozen");
        }
        else {
            String username = user.getUsername();
            int numLent = user.getNumLent();
            int numBorrowed = user.getNumBorrowed();
            int borrowLendThreshold = TradeSystem.adminUser.userManager.getBorrowLendThreshold();
            UnfreezeRequestAlert alert = new UnfreezeRequestAlert(username, numLent, numBorrowed, borrowLendThreshold);
            TradeSystem.adminUser.alertAdmin(alert);
            System.out.println("Your request has been sent");
        }
    }

    public void viewPendingTrades(User user){
        int choice = 0;
        while (choice != 0 ) {
            ArrayList<Trade> userTrades = TradeSystem.adminUser.userManager.searchPendingTradesByUser(user);
            System.out.println("Options:");
            System.out.println("(1) Exit this menu");
            System.out.println("====================");
            System.out.println("Your pending trades:");
            for (Trade trade : userTrades) {
                System.out.println(TradeSystem.adminUser.userManager.tradeToString(trade));
            }
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            if (choice != 1){
                System.out.println("Your choice was not valid, please pick a valid choice.");
            }
        }
    }

    public void viewActiveTempTrades(User user) {
        int input = -1;
        ArrayList<TemporaryTrade> userTrades = TradeSystem.adminUser.userManager.searchActiveTempTradesByUser(user);
        System.out.println("Options:");
        System.out.println("(1) Exit this menu");
        System.out.println("====================");
        System.out.println("Your active temporary trades:");
        for (Trade trade : userTrades) {
            System.out.println(TradeSystem.adminUser.userManager.tradeToString(trade));
        }
        while (input != 1) {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
            if (input != 1) {
                System.out.println("Your choice was not valid, please pick a valid choice.");
            }
        }

    }

}
