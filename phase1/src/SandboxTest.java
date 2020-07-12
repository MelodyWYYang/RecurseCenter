import AlertPack.ItemValidationRequestAlert;
import AlertPack.TradeRequestAlert;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SandboxTest {

    public static void main(String[] args) throws UserNameTakenException {
        UserManager usermanager = new UserManager();
        TradeCreator tradeCreator = new TradeCreator();
        TradeHistories tradeHistories = new TradeHistories();

        usermanager.createUser("user1", "password");
        usermanager.createUser("user2", "password");
        usermanager.createUser("user3", "password");
        usermanager.createUser("user4", "password");
        Item item1 = new Item("Bike", 1);
        Item item2 = new Item("Skateboard", 2);
        Item item3 = new Item("Hoverboard", 3);
        Item item4 = new Item("Peanut", 4);
        Item item5 = new Item("Acorn", 5);
        Item item6 = new Item("Almond", 6);
        ArrayList<Integer> trade12 = new ArrayList<Integer>();
        trade12.add(1);
        ArrayList<Integer> trade21 = new ArrayList<Integer>();
        trade21.add(4);
        ArrayList<Integer> trade13 = new ArrayList<Integer>();
        trade13.add(2);
        ArrayList<Integer> trade31 = new ArrayList<Integer>();
        trade31.add(5);
        ArrayList<Integer> trade14 = new ArrayList<Integer>();
        trade14.add(3);
        ArrayList<Integer> trade41 = new ArrayList<Integer>();
        trade41.add(6);

        Trade trade1 = new Trade("user1", "user2", trade12, trade21, 1);
        trade1.setTimeOfTrade(LocalDateTime.now());
        Trade trade2 = new Trade("user1", "user3", trade13, trade31, 2);
        trade2.setTimeOfTrade(LocalDateTime.now());
        Trade trade3 = new Trade("user1", "user4", trade14, trade41, 3);
        tradeHistories.completedTrades.add(trade1);
        trade3.setTimeOfTrade(LocalDateTime.now());

        tradeHistories.completedTrades.add(trade2);
        tradeHistories.completedTrades.add(trade3);

        usermanager.listUsers.get(0).addAvailableItem(item4);
        usermanager.listUsers.get(0).addAvailableItem(item5);
        usermanager.listUsers.get(0).addAvailableItem(item6);
        usermanager.listUsers.get(1).addAvailableItem(item1);
        usermanager.listUsers.get(2).addAvailableItem(item2);
        usermanager.listUsers.get(3).addAvailableItem(item3);

        System.out.println(tradeHistories.getNRecentItems(usermanager,"user1", 3));
        System.out.println(tradeHistories.getNumTradesThisWeek("user1"));
        System.out.println(tradeHistories.getTopNTradingPartners("user1", 3));
    }


}