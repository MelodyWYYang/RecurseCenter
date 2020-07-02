import AlertPack.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class UserAlertManager {

    public void handleAlertQueue(ArrayList<UserAlert> alerts){
        while(!(alerts.size() == 0)){
            UserAlert alert = alerts.get(0);
            handleAlert(alert);
            alerts.remove(alert);
        }
    }

    private void handleAlert(UserAlert alert) {
        System.out.println(alert); //All alerts have a toString description

        if (alert instanceof FrozenAlert) {
            handleFrozenAlert();
        } else if (alert instanceof ExpirationAlert) {
            handleExpirationAlert((ExpirationAlert) alert);
        } else if (alert instanceof TradeRequestAlert){

            handleTradeRequestAlert((TradeRequestAlert) alert);

        }
        else if (alert instanceof TradeAcceptedAlert) {
            handleTradeAcceptedAlert();
        } else if (alert instanceof TradeDeclinedAlert){
            handleTradeDeclinedAlert();
        } else if (alert instanceof TradeCancelledAlert) {
            handleTradeCancelledAlert();
        } else if (alert instanceof  TradeRequestCancelledAlert) {
            handleTradeRequestCancelledAlert();
        }else if (alert instanceof ItemValidationDeclinedAlert){
            handleItemValidationDeclinedAlert((ItemValidationDeclinedAlert) alert);
        } else if (alert instanceof TradePastDateAlert) {
            handleTradePastDateAlert((TradePastDateAlert) alert);
        } else if (alert instanceof MessageAlert) {
            handleMessageAlert();
        }

            //Each alert needs a handle method for its type, which prints/takes input and calls corresponding functions to
            //  handle the alert on the enduser side of things. See google doccument for specifics on alerts and their
            //  handling process.

    }

    private void handleFrozenAlert(){
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) flag = false;
        }
    }

    private void handleExpirationAlert(ExpirationAlert alert){
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
            User user = UserManager.searchUser(alert.getUsername());
            TemporaryTrade trade = TradeSystem.userManager.serachTemporaryTrade(alert.getTradeId());
            TradeSystem.userManager.confirmReExchange(user, trade);
            System.out.println("Trade ReExchange confirmed");
        }
    }

    private void handleTradeRequestAlert(TradeRequestAlert a){

        boolean canEditTrade = true;
        int input = 0;

        Scanner scan = new Scanner(System.in);
        Trade trade = TradeSystem.userManager.searchPendingTradeRequest(a.getTradeID());

        User thisUser;

        int numEditsRemaining;
        if (a.getSenderUserName().equals(trade.getUsername1())){
            numEditsRemaining = 3 - trade.getUser2NumRequests();
            thisUser = TradeSystem.userManager.searchUser(trade.getUsername2());
        }else{
            numEditsRemaining = 3 - trade.getUser1NumRequests();
            thisUser = TradeSystem.userManager.searchUser(trade.getUsername1());
        }

        if (numEditsRemaining == 0){
            canEditTrade = false;
        }

        System.out.println("(1) Accept \n (2) Decline \n (3) Edit time and Place (" + numEditsRemaining +
                " edits remaining)");

        input = scan.nextInt();

        assert thisUser != null;

        if (input == 1){
            TradeSystem.userManager.acceptTradeRequest(trade, thisUser);
            System.out.println("Trade Request Accepted. Meet up with the person at the time and place specified above."+
                    "Remember to login to confirm the trade afterwords!");
        } else if (input == 2){
            TradeSystem.userManager.declineTradeRequest(trade, thisUser);
            System.out.println("Trade Request Declined.");
        } else if (input == 3){
            System.out.println("Editing Trade Request. \n Enter new meeting time (format: yyyy-MM-dd HH:mm: \n");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            boolean stringNotFound = true;
            LocalDateTime meetingTime = null;
            while(stringNotFound) {
                //TODO: Ensure that scan.nextLine() is the method that gets the String input of the next line.
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
            TradeSystem.userManager.editTradeRequest(trade, meetingTime, inputMeetingPlace, thisUser);
            System.out.println("Trade successfully edited. Meeting at " + inputMeetingPlace + " at " + meetingTime +
                    ".");
        }
    }

    private void handleTradeAcceptedAlert(){

        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }

    }

    private void handleTradeDeclinedAlert(){

        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }

    }


    private void handleTradeCancelledAlert() {

        boolean handled = false;

        int input = 0;

        while (!handled){
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }
    }

    private void handleTradeRequestCancelledAlert() {

        boolean handled = false;

        int input = 0;

        while (!handled) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Dismiss");
            input = scan.nextInt();
            if (input == 1) handled = true;
        }
    }
    public void handleItemValidationDeclinedAlert(ItemValidationDeclinedAlert alert){
        //It doesn't make sense to finish this method until we have set up the functionality to request item validation
        // I will finish this method when we do so. - Louis
        System.out.println(alert.toString());
        System.out.println("(1) Dismiss");
        System.out.println("(2) Send a new item validation request");
        int choice = optionChoice(2);
        if (choice == 2){
            //TODO finish this method by sending a new item validation request - Louis
        }
    }

    public void handleTradePastDateAlert(TradePastDateAlert alert){

        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1) Report the other user \n (2) Confirm Trade");
            input = scan.nextInt();
            if (input == 1) {
                // report the other user. rendReport not implemented. will update
            }
            if (input == 2){
                User user = UserManager.searchUser(alert.getUsername());
                // confirm trade
        }

        }
    }

    private void handleMessageAlert(){

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
        while(choice > x || choice <= 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 1 and " + x);
        }
        return choice;


    }
}
