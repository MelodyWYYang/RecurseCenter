import java.io.Serializable;

public class ItemValidationRequest implements Serializable{
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected String usernameOfOwner;
    protected String name;
    protected String description;
    protected int itemID;
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

    public String getObj() {
        return name;
    }

    public String getOwner() {
        return usernameOfOwner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
