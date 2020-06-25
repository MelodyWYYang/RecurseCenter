import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Optional;

public class AdminUser {
    //author: ___________ in group 0110 for CSC207H1 summer 2020 project

    private String username;
    private String password;

    private ArrayList<ItemValidationRequest> itemValidationQueue;
    private ArrayList<User> accountsToFreezeQueue;

    protected Optional<Item> pollValidationRequest() {
        if (itemValidationQueue.size() == 0){
            return Optional.empty();
        }

    }

    public void dequeueAndFreeze(ArrayList<User> accountsToFreezeQueue){
        User user = accountsToFreezeQueue.get(0);
        user.isFrozen = true;
        accountsToFreezeQueue.remove(0);
    }

}
