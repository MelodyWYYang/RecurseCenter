import java.util.ArrayList;

public class Item {
    //author: Tian Yue Dong in group 0110 for CSC207H1 summer 2020 project

    private String name;
    private String description;
    private String id;

    private User owner;
    private User userThatHasPossession;

    protected ArrayList<String> tags;

    public Item(String name, String description, String id, User owner){
        this.name = name;
        this.description = description;
        this.id = id;
        this.owner = owner;
        userThatHasPossession = owner;
    }

    public Item(String name, String id, User owner){
        this.name = name;
        this.id = id;
        this.owner = owner;
        userThatHasPossession = owner;
    }

    //setters

    public void setOwner(User owner) { this.owner = owner; }

    public void setUserThatHasPossession(User userThatHasPossession) {
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
        tags.remove(tag);
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

    public User getOwner() {
        return owner;
    }

    public User getUserThatHasPossession() {
        return userThatHasPossession;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
