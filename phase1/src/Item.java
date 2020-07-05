import javax.print.DocFlavor;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    //author: Tian Yue Dong in group 0110 for CSC207H1 summer 2020 project

    private String name;
    private String description;
    private final int id;
    private static int idGenerator = 1;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = idGenerator;
        idGenerator ++;
    }

    public Item(String name){
        this.name = name;
        this.id = idGenerator;
        idGenerator ++;
    }

    @Override
    public String toString() {
        return name + "\n" + "item Id: " + id + "\n" + "Description: " + description;
    }

    //setters

    /**
     * @param description the description of the string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    //getters

    /**
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the ID of the item
     */
    public int getId() {
        return id;
    }


}
