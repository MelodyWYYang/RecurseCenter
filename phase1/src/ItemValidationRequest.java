public class ItemValidationRequest {
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected User owner;
    protected Item obj;
    protected String description;

    public ItemValidationRequest(User owner, Item obj, String desc){
        this.description = desc;
        this.obj = obj;
        this.owner = owner;
    }
    public ItemValidationRequest(User owner, Item obj){
        this.obj = obj;
        this.owner = owner;
    }

    public Item getObj() {
        return obj;
    }

    public User getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
