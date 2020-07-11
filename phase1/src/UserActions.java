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


    public void runUserMenu(UserManager userManager, TradeCreator tradeCreator, User user){
        mainMenu(userManager, tradeCreator, user);
    }

    /**
     * Display the main menu to user, take user's input to implement the corresponding action
     * @param user user logged in making changes or viewing statuses
     */
    public void mainMenu(UserManager userManager, TradeCreator tradeCreator, User user){

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
                    System.out.println("Please enter a number from 0 to 6");
                } else if (input == 1) {
                    viewItemAndWishlist(userManager, user);
                    valid_input = true;
                } else if (input == 2) {
                    runStats(userManager, tradeCreator, user);
                    valid_input = true;
                } else if (input == 3) {
                    sendUnfreezeRequest(userManager, tradeCreator, user);
                    valid_input = true;
                } else if (input == 4) {
                    viewAllUsers(userManager, tradeCreator, user);
                    valid_input = true;
                } else if (input == 5) {
                    viewPendingTrades(tradeCreator, user);
                    valid_input = true;
                } else if (input == 6) {
                    viewActiveTempTrades(tradeCreator, user);
                    valid_input = true;
                } else if (input == 0){
                    valid_input = true;
                    running = false;
                }
        }
        }
    }

    /**
     * Allow user to view their available items (inventory) and wishlist
     * @param user user logged in viewing their items
     */
    public void viewItemAndWishlist(UserManager userManager, User user){
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
                    "\n (3) Remove an item from your wishlist\n (0) Exit to main menu");
            input = scan.nextInt();
            if (input < 1 || input > 3){
                System.out.println("Please enter a number from 1 to 3");
            } else flag = false;

         if (input == 1){
             String name = null;
             System.out.println("Please enter the name of your item");
             scan.nextLine(); //This awfulness is needed to prevent it from skipping a line. - Louis
             name = scan.nextLine();
             System.out.println("Please enter the item description");
             String description = scan.nextLine();
             String username = user.getUsername();
             userManager.sendValidationRequest(name,description,username);
         } else if (input == 2){
             System.out.println("Please enter the ID of the item you wish to remove");
             int itemID = scan.nextInt();
             for (Item item: user.getAvailableItems()){
                 if (item.getId() == itemID) {
                     user.getAvailableItems().remove(item);
                     System.out.println("Item deleted");
                 } else System.out.println("Invalid item id");
             }

         } else if (input == 0) {
             return;

            }else {
             System.out.println("Please enter the name of the item on your wishlist you wish to remove");
             String wishlistitem = scan.nextLine();
             for (String itemName: user.getWishlistItemNames()){
                 if (itemName.equals(wishlistitem)) {
                     user.getWishlistItemNames().remove(wishlistitem);
                 }
             }
         }

        }

    }

    /**
     * Allow user to view all the users in the trading system
     * @param userViewing user logged in viewing other users
     */
    public void viewAllUsers(UserManager userManager, TradeCreator tradeCreator, User userViewing){
        boolean handled = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("--- View other users ---");
        System.out.println("Enter a number to view a User's page:");
        int page = 1;
        ArrayList<User> allUsers = userManager.getListUsers();
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
                    viewUser(userManager, tradeCreator, allUsers.get(input - 1), userViewing);
                }
            }
        }
    }

    /**
     * Allow userViewing to do the followings:
     * (1) send a message to userToView
     * (2) add one of userToView's items to the wishlist
     * (3) send a trade request to userToView
     * @param userToView user that is being viewed
     * @param userViewing user logged in that is viewing other user
     */
    private void viewUser(UserManager userManager, TradeCreator tradeCreator, User userToView, User userViewing) {
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
                scan.nextLine();//this uglyness is needed to prevent the scanner from skipping a line - Louis
                String message = scan.nextLine();
                userManager.sendMessageToUser(userViewing, userToView, message);
                System.out.println("Sent message to " + userToView.getUsername() + ": \"" + message + "\"");
            } else if (input == 2){
                System.out.println("Enter the name of the item you would like added to your wishlist:\n");
                String itemString = scan.nextLine();
                userManager.addToWishlist(userViewing, itemString);
            } else if (input == 3 && !userToView.getFrozen()){
                formTradeRequest(tradeCreator, userViewing, userToView);
            } else if (input == 0){
                handled = true;

            }
        }
    }

    /**
     * Allow userSending to send a trade request to userReceiving
     * @param userSending user logged in that sends the trade request
     * @param userReceiving user that will receive the trade request
     */
    private void formTradeRequest(TradeCreator tradeCreator, User userSending, User userReceiving) {
        //TODO break this method into helpers
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
                while (!validYNInput) {
                    System.out.println("Would you like to add another item? (Y/N)");
                    String anotherItem = scan.nextLine();
                    if (anotherItem.equals("Y")) {
                        validYNInput = true;
                    } else if (anotherItem.equals("N")) {
                        System.out.println("Moving on...");
                        validYNInput = true;
                        finished = true;
                    } else {
                        System.out.println("Please enter Y or N.");
                    }
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
            System.out.println("Enter the ID of an item you want to offer:");
            System.out.println("Your available items to trade:");
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
                System.out.println("Invalid ID. Please try again.");
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

        System.out.println("Enter a meeting time (format: yyyy-MM-dd HH:mm: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        boolean stringNotFound = true;
        LocalDateTime meetingTime = null;
        while (stringNotFound) {

            String inputDateTime = scan.nextLine();
            try {
                meetingTime = LocalDateTime.parse(inputDateTime, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format for Date and Time, Try again (format: yyyy-MM-dd HH:mm):");
                continue;
            }
            stringNotFound = false;
        }

        System.out.println("Enter a meeting place: ");
        String meetingPlace = scan.nextLine();
        System.out.println("Should this be a temporary trade? (Y/N)");
        boolean isTempTrade = false;
        boolean validYN = false;
        while(!validYN) {
            String tempYN = scan.nextLine();
            if (tempYN.equals("Y")){
                validYN = true;
                isTempTrade = true;
            } else if (tempYN.equals("N")){
                validYN = true;
                isTempTrade = false;
            } else{
                System.out.println("Please enter either Y or N:");
            }
        }

        Boolean canBeProcessed;
        if (isTempTrade) {
            canBeProcessed = tradeCreator.sendTradeRequest(userSending, userReceiving, itemIDsRecieved, itemIDsSent,
                    meetingTime, meetingPlace);
        } else {
            canBeProcessed = tradeCreator.sendTemporaryTradeRequest(userSending, userReceiving, itemIDsRecieved, itemIDsSent,
                    meetingTime, meetingPlace);
        }
        if (!canBeProcessed){
            System.out.println("Your trade could not be processed. This could have happened if you have completed " +
                    "too many trades this week or if one of the users was frozen");
        }else{
        System.out.println("Successfully created and sent trade request. You will be notified when they respond.");
        }
    }

    /**
     * Allow user to view the following statuses:
     * (1) Number of items they have borrowed
     * (2) Number of items lent
     * (3) Frozen status
     * (4) Number of pending trades that has not been completed
     * (5) Number of transactions in the week
     * (6) Items that they recently traded away
     * (7) Three most frequent trading partners
     * @param user user logged in viewing the statuses
     */
    public void runStats(UserManager userManager, TradeCreator tradeCreator, User user) {
        //TODO: Fix this so we're not stacking calls on top of eachother.
        int input = 0;
        boolean handled = false;
        while (!handled) {
            while (true) {
                Scanner scan = new Scanner(System.in);
                System.out.println("--- View user stats ---");
                System.out.println("(1) View number of items user has borrowed \n(2) View number of items user has lent" +
                        "\n(3) View frozen status \n(4) View number of trades involving user that have not been" +
                        "completed \n(5) View number of transactions this week \n(6) View items recently traded away" +
                        "\n(7) View most frequent trading partner's \n(8) Return to \"User Menu\"");
                input = scan.nextInt();
                if (input > 8 || input < 1) {
                    System.out.println("Please enter a number from 1 to 8");
                } else break;
            }
            if (input == 1) {
                System.out.println("You have borrowed " + Integer.toString(user.getNumBorrowed()) + " items.");

            } else if (input == 2) {
                System.out.println("You have lent " + Integer.toString(user.getNumLent()) + " items.");

            } else if (input == 3) {
                if (user.getFrozen()) {
                    System.out.println("Your account has been frozen");
                } else {
                    System.out.println("Your account is not frozen");
                }

            } else if (input == 4) { //Other methods need access to UserManager methods
                int incompleteTrades = tradeCreator.getNumIncompTrades(user.getUsername());
                System.out.println("Your account has made " + Integer.toString(incompleteTrades) +
                        " incomplete transactions");

            } else if (input == 5) {
                int weeklyTransactions = tradeCreator.tradeHistories.getNumTradesThisWeek(user.getUsername());
                System.out.println("Your account has made " + Integer.toString(weeklyTransactions) +
                        " transactions this week");

            } else if (input == 6) {
                ArrayList<Item> recentItems = tradeCreator.tradeHistories.getNRecentItems(userManager, user.getUsername(), 3);
                System.out.println(recentItems);

            } else if (input == 7) {
                ArrayList<String> favouriteParnters = tradeCreator.tradeHistories.getTopNTradingPartners(user.getUsername(), 3);
                System.out.println(favouriteParnters);
            } else if (input == 8) {
                handled = true;
            }
        }
    }

    /**
     * Send a unfreeze request to admin
     * @param user user that sends the request
     */
    public void sendUnfreezeRequest(UserManager userManager, TradeCreator tradeCreator, User user) {
        if (!user.getFrozen()) {
            System.out.println("Your account is not frozen");
        }
        else {
            String username = user.getUsername();
            int numLent = user.getNumLent();
            int numBorrowed = user.getNumBorrowed();
            int borrowLendThreshold = tradeCreator.getBorrowLendThreshold();
            UnfreezeRequestAlert alert = new UnfreezeRequestAlert(username, numLent, numBorrowed, borrowLendThreshold);
            userManager.alertAdmin(alert);
            System.out.println("Your request has been sent");
        }
    }

    /**
     * Allow user to view their pending trade history
     * @param user user logged in
     */
    public void viewPendingTrades(TradeCreator tradeCreator, User user){
        int choice = 0;
        while (choice != 0 ) {
            ArrayList<Trade> userTrades = tradeCreator.searchPendingTradesByUser(user);
            System.out.println("Options:");
            System.out.println("(1) Exit this menu");
            System.out.println("====================");
            System.out.println("Your pending trades:");
            for (Trade trade : userTrades) {
                //System.out.println(tradeToString(trade));
                System.out.println("tradeToString should be called here");
            }
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            if (choice != 1){
                System.out.println("Your choice was not valid, please pick a valid choice.");
            }
        }
    }

    /**
     * Allow user to view their active temporary trade history
     * @param user user logged in
     */
    public void viewActiveTempTrades(TradeCreator tradeCreator, User user) {
        int input = -1;
        ArrayList<TemporaryTrade> userTrades = tradeCreator.tradeHistories.searchActiveTempTradesByUser(user);
        System.out.println("Options:");
        System.out.println("(1) Exit this menu");
        System.out.println("====================");
        System.out.println("Your active temporary trades:");
        for (Trade trade : userTrades) {
            //TODO: This tradeToString method should be implemented in our presenter layer. it can be called from there.
            //System.out.println(tradeToString(trade));
            //the below is our substitute for now:
            System.out.println("tradeToString should be called here");
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
