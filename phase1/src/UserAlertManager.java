import AlertPack.ExpirationAlert;
import AlertPack.FrozenAlert;
import AlertPack.UserAlert;

import java.sql.SQLOutput;
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

    private void handleAlert(UserAlert alert ) {
        boolean flag = true;
        System.out.println(alert); //All alerts have a toString description
        int input = 0;

        if (alert instanceof FrozenAlert) {
            while (flag) {
                Scanner scan = new Scanner(System.in);
                System.out.println("(1) Dismiss");
                input = scan.nextInt();
                if (input == 1) flag = false;
            }

        } else if (alert instanceof ExpirationAlert) {
            while (flag) {
                Scanner scan = new Scanner(System.in);
                System.out.println("(1) Report the other user /n (2) Confirm ReExchange");
                input = scan.nextInt();
                if (input == 1 || input == 2) flag = false;
            }
            if (input == 1) {
                // report the other user
            } else {
                //UserManager.confirmReExchange(searchUser(trade.getUsername1()), trade)
                //dependent on usermanager
            }


            else if (alert instance of PUT_YOUR_ALERT_HERE){
                // WHAT IT DO BABY
            }
        }


            //Each alert needs a handle method for its type, which prints/takes input and calls corresponding functions to
            //  handle the alert on the enduser side of things. See google doccument for specifics on alerts and their
            //  handling process.

        }
    }
}