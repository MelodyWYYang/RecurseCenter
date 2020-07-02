package AlertPack;

import java.io.Serializable;

public class ItemValidationDeclinedAlert extends UserAlert implements Serializable {
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected String usernameOfOwner;
    protected String name;
    protected String description;
    protected final int itemID;
    protected String message;

    public ItemValidationDeclinedAlert(String owner, String obj, String desc, int itemID, String message){
        this.description = desc;
        this.name = obj;
        this.usernameOfOwner = owner;
        this.itemID = itemID;
        this.message = message;
    }
    public ItemValidationDeclinedAlert(String owner, String obj, int itemID, String message){
        this.name = obj;
        this.usernameOfOwner = owner;
        this.itemID = itemID;
        this.message = message;
    }

    /**
     *
     * @return the name or title of the item.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the username of the user who is submitting the item validation request.
     */
    public String getOwner() {
        return usernameOfOwner;
    }

    /**
     *
     * @return the description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the admin message attached to the alert.
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return ID number of the item
     */
    public int getItemID() {
        return itemID;
    }
    @Override
    public String toString(){
        return "Your item validation request has been declined for the following reason: \n" +message+ "\nUser: " + usernameOfOwner + "Item name: " + name + "\nItem description: " +
                description + "\nItem ID number: " + itemID ;
    }
}
