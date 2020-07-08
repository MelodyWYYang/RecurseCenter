import java.util.Scanner;

public class AdminActions {



    /** Method that takes user input and changes the threshold value (The necessary difference between the number of
     * items users have lent and borrowed before they can make another transaction)
     * @param admin AdminUser logged in making changes
     */
    protected static void setThreshold(AdminUser admin) {
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
    protected static void addNewAdmin(AdminUser admin) {
        //TODO: ensure the username is unique.
        boolean flag = true;
        String inputUsername;
        String inputPassword;
        while (flag) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the username of the administrator you want to add: ");
            inputUsername = scan.next();
            System.out.println("Enter the password of the administrator you want to add: ");
            inputPassword = scan.next();
            TradeSystem.adminUser.addLogin(inputUsername, inputPassword);
            flag = false;
        }
    }
}
