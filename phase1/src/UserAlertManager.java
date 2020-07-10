import AlertPack.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class UserAlertManager {

    /**
     * Iterate through each alert in alerts, handle it and remove it from the list
     * @param alerts list of alert that are sent in
     */
    public void handleAlertQueue(UserManager userManager, TradeCreator tradeCreator, ArrayList<UserAlert> alerts){
        while(!(alerts.size() == 0)){
            UserAlert alert = alerts.get(0);
            handleAlert(userManager, tradeCreator, alert);
            alerts.remove(alert);
        }
    }

    /**
     * Handle alert based on its coresponding type
     * @param alert alert sent in
     */
    private void handleAlert(UserManager userManager, TradeCreator tradeCreator, UserAlert alert) {

        if (alert instanceof FrozenAlert) {
            handleFrozenAlert((FrozenAlert) alert);
        } else if (alert instanceof ExpirationAlert) {
            handleExpirationAlert(userManager, tradeCreator, (ExpirationAlert) alert);
        } else if (alert instanceof TradeRequestAlert){
            handleTradeRequestAlert(userManager, tradeCreator, (TradeRequestAlert) alert);
        } else if (alert instanceof TradeAcceptedAlert) {
            handleTradeAcceptedAlert(userManager, tradeCreator, (TradeAcceptedAlert) alert);
        } else if (alert instanceof TradeDeclinedAlert){
            handleTradeDeclinedAlert((TradeDeclinedAlert) alert);
        } else if (alert instanceof TradeCancelledAlert) {
            handleTradeCancelledAlert((TradeCancelledAlert) alert);
        } else if (alert instanceof  TradeRequestCancelledAlert) {
            handleTradeRequestCancelledAlert((TradeRequestCancelledAlert) alert);
        }else if (alert instanceof ItemValidationDeclinedAlert){
            handleItemValidationDeclinedAlert(userManager, (ItemValidationDeclinedAlert) alert);
        } else if (alert instanceof TradePastDateAlert) {
            handleTradePastDateAlert(userManager, tradeCreator, (TradePastDateAlert) alert);
        } else if (alert instanceof MessageAlert) {
            handleMessageAlert((MessageAlert) alert);
        }

            //Each alert needs a handle method for its type, which prints/takes input and calls corresponding functions to
            //  handle the alert on the end user side of things. See google document for specifics on alerts and their
            //  handling process.

    }


    private void handleFrozenAlert(FrozenAlert a){
        System.out.println("Your account has been frozen by administrator at " +
                "\n" + "You have borrowed: " + a.getNumBorrowedofUser() + "items" +
                "\n" + "You have lent " + a.getNumLentofUser() + " items" +
                "\n" + "You need to lend " + a.getThreshholdNumofUser()+ " items before you can borrow");
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) flag = false;
        }
    }

    private void handleExpirationAlert(UserManager userManager, TradeCreator tradeCreator, ExpirationAlert alert){

        System.out.println("The following TemporaryTrade has expired at" + alert.getDueDate() + ":\n" +
                tradeToString(userManager, tradeCreator.tradeHistories.searchActiveTemporaryTrade(alert.getTradeId())));
        boolean flag = true;
        int input = 0;

        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Report the other user \n (2) Confirm ReExchange");
            input = scan.nextInt();
            if (input == 1 || input == 2) flag = false;
        }
        if (input == 1) {
            // report the other user
        } else {
            User user = userManager.searchUser(alert.getUsername());
            TemporaryTrade trade = tradeCreator.tradeHistories.searchTemporaryTrade(alert.getTradeId());
            tradeCreator.tradeHistories.confirmReExchange(userManager, user, trade);
            System.out.println("Trade ReExchange confirmed");
        }
    }

    private void handleTradeRequestAlert(UserManager userManager, TradeCreator tradeCreator, TradeRequestAlert a){

        System.out.println( a.getSenderUserName() + " has proposed the following trade: \n" +
                tradeToString(userManager, tradeCreator.searchPendingTradeRequest(a.getTradeID())));
        boolean canEditTrade = true;
        int input = 0;

        Scanner scan = new Scanner(System.in);
        Trade trade = tradeCreator.searchPendingTradeRequest(a.getTradeID());

        User thisUser;

        int numEditsRemaining;
        if (a.getSenderUserName().equals(trade.getUsername1())){
            numEditsRemaining = 3 - trade.getUser2NumRequests();
            thisUser = userManager.searchUser(trade.getUsername2());
        }else{
            numEditsRemaining = 3 - trade.getUser1NumRequests();
            thisUser = userManager.searchUser(trade.getUsername1());
        }

        if (numEditsRemaining == 0){
            canEditTrade = false;
        }

        System.out.println("(1) Accept \n (2) Decline \n (3) Edit time and Place (" + numEditsRemaining +
                " edits remaining)");

        input = scan.nextInt();

        assert thisUser != null;

        if (input == 1){
            tradeCreator.acceptTradeRequest(trade, thisUser.getUsername());
            System.out.println("Trade Request Accepted. Meet up with the person at the time and place specified above."+
                    "Remember to login to confirm the trade afterwords!");
        } else if (input == 2){
            tradeCreator.declineTradeRequest(trade, thisUser.getUsername());
            System.out.println("Trade Request Declined.");
        } else if (input == 3){
            System.out.println("Editing Trade Request. \n Enter new meeting time (format: yyyy-MM-dd HH:mm: \n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            boolean stringNotFound = true;
            LocalDateTime meetingTime = null;
            while(stringNotFound) {

                String inputDateTime = scan.nextLine();
                try {
                    meetingTime = LocalDateTime.parse(inputDateTime, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid format for Date and Time, Try again (format: yyyy-MM-dd HH:mm) :");
                    continue;
                }
                stringNotFound = false;
            }

            System.out.println("Enter new meeting place:");
            String inputMeetingPlace = scan.nextLine();

            //TODO: Ensure that this is not always null (the compiler complains that it is but I have my doubts).
            assert meetingTime != null;
            tradeCreator.editTradeRequest(userManager, trade, meetingTime, inputMeetingPlace, thisUser.getUsername());
            System.out.println("Trade successfully edited. Meeting at " + inputMeetingPlace + " at " + meetingTime +
                    ".");
        }
    }

    private void handleTradeAcceptedAlert(UserManager userManager, TradeCreator tradeCreator, TradeAcceptedAlert a){

        System.out.println(a.getAcceptingUsername() +
                " has accepted the following trade request: \n" + tradeToString(userManager,
                tradeCreator.searchPendingTrade(a.getTradeID())));
        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }

    }

    private void handleTradeDeclinedAlert(TradeDeclinedAlert a){
        System.out.println(a.getDecliningUserName() + " has declined your trade request with ID " + a.getTradeID());
        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }

    }

    private void handleTradeCancelledAlert(TradeCancelledAlert a) {

        System.out.println("The following pending trade has been cancelled as one of the users is no longer in possession of " +
                "a item in the proposed trade. Trade ID: " + a.getTradeID());
        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }
    }

    private void handleTradeRequestCancelledAlert(TradeRequestCancelledAlert a) {

        System.out.println("The following trade request has been cancelled as one of the users is no " +
                "longer in possession of an item in the proposed trade. Trade ID: " + a.getTradeID() );
        boolean handled = false;

        int input = 0;

        while (!handled) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }
    }

    private void handleItemValidationDeclinedAlert(UserManager userManager, ItemValidationDeclinedAlert a){

        System.out.println("Your item validation request has been declined for the following reason: \n" +
                a.getMessage()+ ".\nUser: " + a.getOwner() + "Item name: " + a.getName() + "\nItem description: " +
                a.getDescription() + "\nItem ID number: " + a.getItemID() );
        //It doesn't make sense to finish this method until we have set up the functionality to request item validation
        // I will finish this method when we do so. - Louis
        System.out.println("(1) Dismiss");
        System.out.println("(2) Send a new item validation request");
        int choice = optionChoice(2);
        if (choice == 2){
            Scanner scan = new Scanner(System.in);
            String name = null;
            System.out.println("Please enter the name of your item");
            scan.nextLine(); //This awfulness is needed to prevent it from skipping a line. - Louis
            name = scan.nextLine();
            System.out.println("Please enter the item description");
            String description = scan.nextLine();
            String username = a.getOwner();
            userManager.sendValidationRequest(name,description,username);
        }
    }

    private void handleTradePastDateAlert(UserManager userManager, TradeCreator tradeCreator,TradePastDateAlert a){

        System.out.println("The following trade expired at" + a.getDueDate()+ "\n" +
                tradeToString(userManager, tradeCreator.searchPendingTrade(a.getTradeId())));
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Confirm Trade\n(2) I didn't show up\n(3) The other person didn't show up");
            input = scan.nextInt();
            if (input == 1) {
                User user = userManager.searchUser(a.getUsername());
                tradeCreator.confirmTrade(userManager, user,
                        tradeCreator.searchPendingTrade(a.getTradeId()));
                System.out.println("Trade confirmed. Your items have been exchanged on the system.");
                //TODO
            }
            else if (input == 2){
                //TODO
            }
        }
    }

    private void handleMessageAlert(MessageAlert a){
        System.out.println("From: " + a.getSenderUsername() + "\n" + a.getMessage());
        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }

    }

    //helper method to ensure the user picks a valid choice, options are between 1 and x - Louis
    private int optionChoice(int x){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter one of the numbers listed above");
        int choice = scanner.nextInt();
        while(choice >= x || choice < 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 1 and " + x);
        }
        return choice;


    }


    /**
     *
     * @param trade a trade object
     * @return a string which describes the two users involved in the trade and the Time & date of the trade.
     *///TradeManager -- consider moving to Trade
    public String tradeToString(UserManager userManager, Trade trade){
        return "User 1: " + trade.getUsername1() + "\nUser 2: " + trade.getUsername2() +
                "\nItems being traded from user 1 to user 2: " + GetItemNamesFromUser1ToUser2(userManager, trade) +
                "\nItems being traded from user 2 to user 1: " + GetItemNamesFromUser2ToUser1(userManager, trade) +
                "\nTime & Date of item exchange: " + trade.getTimeOfTrade().toString() +
                "\nLocation of Trade: " + trade.getMeetingPlace() + "\nTradeID: " + trade.getTradeID();
    }
    // helper method which lists the names of the items going from user 1 to user 2 - Louis
    private String GetItemNamesFromUser1ToUser2(UserManager userManager, Trade trade){
        StringBuilder stringBuilder = new StringBuilder();
        for(int itemID: trade.getItemIDsSentToUser2()){
            Item item = userManager.searchItem(userManager.searchUser(trade.getUsername1()), itemID);
            stringBuilder.append(item.getName()).append(" ");
            return stringBuilder.toString();
        }
        return null;
    }
    // helper method which lists the names of the items going from user 2 to user 1 - Louis
    private String GetItemNamesFromUser2ToUser1(UserManager userManager, Trade trade){
        StringBuilder stringBuilder = new StringBuilder();
        for(int itemID: trade.getItemIDsSentToUser1()){
            Item item = userManager.searchItem(userManager.searchUser(trade.getUsername2()), itemID);
            stringBuilder.append(item.getName()).append(" ");
            return stringBuilder.toString();
        }
        return null;
    }
}
