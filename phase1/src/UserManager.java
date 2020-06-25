import java.util.ArrayList;

public class UserManager {
    //author: Jinyu Liu in group 0110 for CSC207H1 summer 2020 project

    public ArrayList<User> listUsers;

    public void createUser() {} //Call the constructor for User, then append to listUsers

    public void acceptTradeRequest() {}

    public void sendTradeRequest() {}

    public void sendValidationRequest(Item item) {AdminUser.itemValidationQueue.add(item);}
}
