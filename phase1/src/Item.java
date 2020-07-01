import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    //author: Tian Yue Dong in group 0110 for CSC207H1 summer 2020 project

    private String name;
    private String description;
    private final int id;

    private String ownerUserName;
    private String userThatHasPossession;

    protected ArrayList<String> tags = new ArrayList<String>();

    public Item(String name, String description, int id){
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Item(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
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

    public int getId() {
        return id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
