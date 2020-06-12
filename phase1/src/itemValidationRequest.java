public class itemValidationRequest {
    protected user owner;
    protected item obj;
    protected String description;

    public itemValidationRequest(user owner, item obj, String desc){
        this.description = desc;
        this.obj = obj;
        this.owner = owner;
    }
    public itemValidationRequest(user owner, item obj){
        this.obj = obj;
        this.owner = owner;
    }

    public item getObj() {
        return obj;
    }

    public user getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
