import AlertPack.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeSystem {

    public static AdminUser adminUser = new AdminUser("admin", "admin");

    static UserAlertManager userAlertManager = new UserAlertManager();

    static AdminAlertManager adminAlertManager = new AdminAlertManager();
    static AdminActions adminActions = new AdminActions();
    static UserActions userActions = new UserActions();


    public static void main(String[] args){
        createAdminUser();
        /*
        //TODO: Create the adminUser.ser file if it does not exist.
        adminUser = FileManager.loadAdminUser("adminUser.ser");

        onStartUp();

        Scanner scan = new Scanner(System.in);
        User loggedIn = null;
        boolean isAdmin = false;
        System.out.println("Welcome to Insert_name_here trading system!\n Would you like to create an account or " +
                "login?\n(1) Create account \n(2)Login to existing account\n(0) Quit");
        int input = optionChoice(2);
        //TODO: Should we make this stuff its own class?
        if (input == 1){
            createAccount();
        } else if (input == 2){
            User x = login();
            if (x == null){
                isAdmin = true;
            } else {
                loggedIn = x;
            }

        } else if(input == 0){
            return;
        }

        if (isAdmin){
            ArrayList<AdminAlert> adminAlerts = adminUser.getAdminAlerts();
            adminAlertManager.handleAlertQueue(adminAlerts);
            adminActions.run();
        } else {
            ArrayList<UserAlert> userAlerts = adminUser.userManager.getAlerts(loggedIn.getUsername());
            userAlertManager.handleAlertQueue(userAlerts);
            userActions.run(loggedIn);
        }

        FileManager.saveAdminToFile(adminUser);

         */
    }
    private static void createAdminUser(){
        AdminUser adminUser = new AdminUser("admin", "admin");
        FileManager.saveAdminToFile(adminUser);
    }

    private static void onStartUp(){
        adminUser.onStartUp();
        adminUser.userManager.onStartUp();
    }

    private static void createAccount(){

        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter your desired username");
                String inputUsername = scan.nextLine();
                System.out.println("Enter your desired password");
                String password = scan.nextLine();
                adminUser.userManager.createUser(inputUsername, password);
                break;
            } catch (UserNameTakenException e) {
                System.out.println("Username taken, try again");
            }
        }


    }

    public static User login(){
        User user = takeUsername();
        if (takePassword(user)){
            return user;
        }
        return null;
    }
    private static User takeUsername(){
        Scanner scanner = new Scanner(System.in);
        User user = null;
        while(user == null) {
            System.out.println("Enter your username:");
            String username = scanner.next();
            if (username.equals("0")){
                return null;// This needs to be changed so that it will return to the main menu. - Louis
            }
            user = adminUser.userManager.searchUser(username);
            if (username.equals(adminUser.getUsername()) && takeAdminPassword(adminUser)){
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
            String pass = scanner.next();
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
    private static Boolean takeAdminPassword(AdminUser adminUser){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter the password for " + adminUser.getUsername() + ":");
            String pass = scanner.next();
            if (pass.equals("0")){
                return false;
            }
            else if (adminUser.checkPassword(pass)) {
                System.out.println("Logged in as " + adminUser.getUsername());
                return true;
            }else{
                System.out.println("Invalid Password. Please try again or enter 0 to return to the main menu."); //currently you will exit the system if you enter 0 - louis
            }
        }
    }

    private static int optionChoice(int x){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter one of the numbers listed above");
        int choice = scanner.nextInt();
        while(choice >= x || choice <= 0){
            System.out.println("The number you entered was not listed above. Please enter a choice between 0 and " + x);
            choice = scanner.nextInt();
        }
        return choice;


    }
}

