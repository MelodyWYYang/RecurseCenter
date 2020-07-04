import AlertPack.ItemValidationRequestAlert;
import AlertPack.TradeRequestAlert;

import java.util.ArrayList;

public class SandboxTest {

    public static void main(String[] args){
        testSearchUser1();

        testSearchItem1();

        testViewAllUsers();
    }


    private static void testViewAllUsers(){
        User jerry = null;
        try {
            jerry = TradeSystem.adminUser.userManager.createUser("Jerry", "passsword1");
            TradeSystem.adminUser.userManager.createUser("Larry", "password2");
            TradeSystem.adminUser.userManager.createUser("Barry", "password3");

            //TODO: Why does this catch?
        } catch (UserNameTakenException e){
            System.out.println("Username Taken");
        }

        UserActions ua = new UserActions();

        ua.mainMenu(jerry);
    }



    private static void testSearchUser1(){
        UserManager um = new UserManager();
        User barry = null;
        try {
            barry = um.createUser("Barry", "password");
        } catch (UserNameTakenException e){
            System.out.println("The username is taken.");
        }

        assert um.searchUser("Barry") == barry;
    }

    private static void testSearchItem1(){
        UserManager um = new UserManager();
        User larry = null;
        try {
            larry = um.createUser("Larry", "password");
        } catch (UserNameTakenException e){
            System.out.println("The username is taken.");
        }

        Item larrysBike = new Item("Bike", 1);

        assert larry != null;
        larry.availableItems.add(larrysBike);

        assert um.searchItem(1) == larrysBike;
    }
}
