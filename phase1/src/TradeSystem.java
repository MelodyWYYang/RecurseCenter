import AlertPack.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
// this should have a builder; now everything in here is private and you can just call the builder
public class TradeSystem {

    private AdminUser adminUser;
    private UserManager userManager;
    private TradeManager tradeManager;

    private UserAlertManager userAlertManager = new UserAlertManager();

    private AdminAlertManager adminAlertManager = new AdminAlertManager();
    private AdminActions adminActions = new AdminActions();
    private UserActions userActions = new UserActions();

    public TradeSystem(){}

    public void createAdminUser(){
        AdminUser adminUser = new AdminUser("admin", "admin");
        FileManager.saveAdminToFile(adminUser);
    }

    public void onStartUp(){
        adminUser.onStartUp();
        tradeManager.onStartUp();
    }

    public User createAccount(){  // does not check that the username is taken
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter your desired username");
                String inputUsername = scan.nextLine();
                System.out.println("Enter your desired password");
                String password = scan.nextLine();
                return userManager.createUser(inputUsername, password);
            } catch (UserNameTakenException e) {
                System.out.println("Username taken, try again");
            }
        }
    }

    /**
     *
     * @return method which allows the user to login to their account.
     */
    public static User login(){
        User user = takeUsername();
        if (user != null && takePassword(user)){
            return user;
        }
        return null;
    }
    private static User takeUsername(){
        Scanner scanner = new Scanner(System.in);
        User user = null;
        while(user == null) {
            System.out.println("Enter your username:");
            String username = scanner.nextLine();
            if (username.equals("0")){
                return null;// This needs to be changed so that it will return to the main menu. - Louis
            }
            user = userManager.searchUser(username);
            if (adminUser.isValidUsername(username) && takeAdminPassword(username)){
                return null;
            }
            else if (user == null){
                System.out.println("Username was not valid. Please try again or enter 0 to return to the main menu.");//currently entering 0 just exits the system - louis
            }
        }
        return user;
    }
    private static boolean takePassword(User user){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter the password for " + user.getUsername() + ":");
            String pass = scanner.nextLine();
            if (pass.equals("0")){
                return false;
            }
            else if (user.checkPassword(pass)) {
                System.out.println("Logged in as " + user.getUsername());
                return true;
            }else{
                System.out.println("Invalid Password. Please try again or enter 0 to return to the main menu."); //currently you will exit the system if you enter 0 - louis
            }
        }
    }
    private static Boolean takeAdminPassword(String username){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter the password for " + username + ":");
            String pass = scanner.nextLine();
            if (pass.equals("0")){
                return false;
            }
            else if (adminUser.checkPassword(username, pass)) {
                System.out.println("Logged in as Admin: " + username);
                return true;
            }else{
                System.out.println("Invalid Password. Please try again or enter 0 to return to the main menu."); //currently you will exit the system if you enter 0 - louis
            }
        }
    }

    protected static int optionChoice(int x){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter one of the numbers listed above");
        int choice = scanner.nextInt();
        while(choice > x || choice < 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 0 and " + x);
            choice = scanner.nextInt();
        }
        return choice;


    }
}

