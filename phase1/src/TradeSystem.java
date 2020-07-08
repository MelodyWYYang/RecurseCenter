import AlertPack.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeSystem {

    public static AdminUser adminUser;

    static UserAlertManager userAlertManager = new UserAlertManager();

    static AdminAlertManager adminAlertManager = new AdminAlertManager();
    static AdminActions adminActions = new AdminActions();
    static UserActions userActions = new UserActions();


    protected static void createAdminUser(){
        AdminUser adminUser = new AdminUser("admin", "admin");
        FileManager.saveAdminToFile(adminUser);
    }

    protected static void onStartUp(){
        adminUser.onStartUp();
        adminUser.userManager.onStartUp();
    }

    protected static User createAccount(){
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter your desired username");
                String inputUsername = scan.nextLine();
                System.out.println("Enter your desired password");
                String password = scan.nextLine();
                return adminUser.userManager.createUser(inputUsername, password);
            } catch (UserNameTakenException e) {
                System.out.println("Username taken, try again");
            }
        }
    }

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
            user = adminUser.userManager.searchUser(username);
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

