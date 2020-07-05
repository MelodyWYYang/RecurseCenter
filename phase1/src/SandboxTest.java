import AlertPack.ItemValidationRequestAlert;
import AlertPack.TradeRequestAlert;

import java.util.ArrayList;

public class SandboxTest {

    public static void main(String[] args){
        //testSearchUser1();

        //testSearchItem1();

        //testViewAllUsers();


    }


    private static void testViewAllUsers(){
        User jerry = null;
        try {
            TradeSystem.adminUser.userManager.createUser("Barry", "password3");
        } catch(UserNameTakenException e) {
            System.out.println("Caught on barry");
        }
        try {
            jerry = TradeSystem.adminUser.userManager.createUser("Jerry", "passsword1");
        } catch(UserNameTakenException e){
            System.out.println("Caught on Jerry");
        }
        try{

            TradeSystem.adminUser.userManager.createUser("Janice", "password2");


            //TODO: Why does this catch?
        } catch (UserNameTakenException e){
            System.out.println("Caught on Janice");
        }

 //       UserActions ua = new UserActions();
 //       User user = TradeSystem.login();//login must be set to public to run this test. Set back to private when done.
 //       if (user != null){
 //           ua.mainMenu(user);
 //       }
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
