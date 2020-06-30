import java.io.Serializable;

public class ItemValidationRequest implements Serializable{
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected String usernameOfOwner;
    protected String name;
    protected String description;
    protected final int itemID;
    private static int idGenerator = 0;

    public ItemValidationRequest(String owner, String obj, String desc){
        this.description = desc;
        this.name = obj;
        this.usernameOfOwner = owner;
        itemID = idGenerator;
        idGenerator++;
    }
    public ItemValidationRequest(String owner, String obj){
        this.name = obj;
        this.usernameOfOwner = owner;
        itemID = idGenerator;
        idGenerator++;
    }

    /**
     *
     * @return the name or title of the item.
     */
    public String getObj() {
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
}
