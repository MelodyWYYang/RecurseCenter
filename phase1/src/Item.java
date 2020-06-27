import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    //author: Tian Yue Dong in group 0110 for CSC207H1 summer 2020 project

    private String name;
    private String description;
    private String id;

    private String ownerUserName;
    private String userThatHasPossession;

    protected ArrayList<String> tags;

    public Item(String name, String description, String id, String owner){
        this.name = name;
        this.description = description;
        this.id = id;
        ownerUserName = owner;
        userThatHasPossession = owner;
    }

    public Item(String name, String id, String owner){
        this.name = name;
        this.id = id;
        ownerUserName = owner;
        userThatHasPossession = owner;
    }

    //setters

    public void setOwner(String owner) { ownerUserName = owner; }

    public void setUserThatHasPossession(String userThatHasPossession) {
        this.userThatHasPossession = userThatHasPossession;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTags(String tag){
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void deleteTag(String tag){
        if(tags.contains(tag)){
            tags.remove(tag);
        }
    }

    //getters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public String getUserThatHasPossession() {
        return userThatHasPossession;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
