import AlertPack.TradeRequestAlert;

import java.util.ArrayList;

public class SandboxTest {

    public static void main(String[] args){

        UserManager um = new UserManager();
        AdminUser au = new AdminUser("Epic_Admin_69", "password");

        um.createUser("Jerry", "JerryIsTheBest123");
        um.createUser("Bob", "BobIsCool22");


        um.sendValidationRequest("Scooter", "Jerry");
        um.sendValidationRequest("Bike", "Jerry");
        um.sendValidationRequest("Pen", "Bob");

        //Note, below is not how we're doing itemvalidation in the final build. This will be determined by
        // Admin's input when moving through their alertQueue upon login.
        au.pollValidationRequest(true);
        au.pollValidationRequest(true);
        au.pollValidationRequest(true);


        ArrayList<Integer> itemIDsSentToUser1 = new ArrayList<Integer>();
        ArrayList<Integer> itemIDsSentToUser2 = new ArrayList<Integer>();

        itemIDsSentToUser1.add(3);
        itemIDsSentToUser2.add(1);
        itemIDsSentToUser2.add(2);

        Trade trade = new Trade("Jerry", "Bob", itemIDsSentToUser1, itemIDsSentToUser2);
        //something about this sequence is fucked. I think its the use of searchItem in createTradeRequestAlert.
        TradeRequestAlert alert = um.createTradeRequestAlert(trade, UserManager.searchUser("Jerry"));
        System.out.println(alert.getTradeString());
    }
}
