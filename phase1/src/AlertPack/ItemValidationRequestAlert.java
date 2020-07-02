package AlertPack;

import java.io.Serializable;

public class ItemValidationRequestAlert extends AdminAlert implements Serializable{
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected String usernameOfOwner;
    protected String name;
    protected String description;
    protected final int itemID;
    private static int idGenerator = 0;

    public ItemValidationRequestAlert(String owner, String obj, String desc){
        this.description = desc;
        this.name = obj;
        this.usernameOfOwner = owner;
        itemID = idGenerator;
        idGenerator++;
    }
    public ItemValidationRequestAlert(String owner, String obj){
        this.name = obj;
        this.usernameOfOwner = owner;
        itemID = idGenerator;
        idGenerator++;
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
     * @param description description of the item.
     */
    public void setDescription(String description) {
        this.description = description;
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
        return "Item validation request\nUser: " + usernameOfOwner + "\nItem name: " + name + "\nItem description: " +
                description + "\nItem ID number: " + itemID;
    }
}
