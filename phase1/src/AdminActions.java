import java.util.Scanner;

public class AdminActions {

    public void run() {
        mainMenu();
    }

    public void mainMenu() {

        boolean running = true;
        while (running) {
            int input = -1;
            Scanner scan = new Scanner(System.in);
            System.out.println("--- Admin Menu --- \n");
            System.out.println("(1) Set borrow/lend threshold \n\"(2) Add new admin" +
                    " \n(0) Quit");
            boolean valid_input = false;
            while (!valid_input) {
                input = scan.nextInt();
                if (input > 2 || input < 0) {
                    System.out.println("Please enter a number from 0 to 2");
                } else if (input == 1) {
                    setThreshold(TradeSystem.adminUser);
                    valid_input = true;
                } else if (input == 2) {
                    addNewAdmin(TradeSystem.adminUser);
                    valid_input = true;
                } else if (input == 0) {
                    valid_input = true;
                    running = false;
                }
            }
        }
    }

    /** Method that takes user input and changes the threshold value (The necessary difference between the number of
     * items users have lent and borrowed before they can make another transaction)
     * @param admin AdminUser logged in making changes
     */
    public void setThreshold(AdminUser admin) {
        boolean flag = true;
        int input = 0;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the new threshold value for lent vs borrowed: ");
            input = scan.nextInt();
            if (input > 50 || input < 0) {
                System.out.println("Please enter a valid threshold number");
            } else {
                TradeSystem.adminUser.userManager.setBorrowLendThreshold(input);
                flag = false;
            }
        }
    }

    /** Method that creates additional logins for AdminUser account
     *
     * @param admin AdminUser logged in making changes
     */
    public void addNewAdmin(AdminUser admin) {
        boolean flag = true;
        String inputUsername;
        String inputPassword;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the username of the administrator you want to add: ");
            inputUsername = scan.next();
            System.out.println("Enter the password of the administrator you want to add: ");
            inputPassword = scan.next();
            TradeSystem.adminUser.createAdmin(inputUsername, inputPassword);
            flag = false;
        }
    }
}
