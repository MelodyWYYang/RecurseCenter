package AlertPack;

import java.io.Serializable;

public class FreezeUserAlert extends AdminAlert implements Serializable {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    private String username; // username of the user
    private String lent; // amount user has lent
    private String borrowed; // amount user has borrowed
    private String thresholdRequired; // difference needed between lent and borrowed

    public FreezeUserAlert(String username, String lent, String borrowed, String thresholdRequired){
        this.username = username;
        this.lent = lent;
        this.borrowed = borrowed;
        this.thresholdRequired = thresholdRequired;
    }

    public String getUsername(){ return username; }

    public String getLent(){ return lent; }

    public String getBorrowed(){ return borrowed; }

    public void setUsername(String username){ this.username = username; }

    public void setLent(String lent){ this.lent = lent; }

    public void setBorrowed(String borrowed){ this.borrowed = borrowed; }

    /**
     * toString() method to return the user's stats to the admin
     * @return a string containing the user's stats and menu options for the admin
     */
    @Override
    public String toString() {
        return "Freeze User Alert" +
                "\n" + username + " has lent: " + lent + " items" +
                "\n" + username + " has borrowed: " + borrowed + " items" +
                "\n" + "Required to lend " + thresholdRequired + " more items than borrowed";
    }
}
