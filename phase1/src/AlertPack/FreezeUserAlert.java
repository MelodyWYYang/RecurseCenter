package AlertPack;

import java.io.Serializable;

public class FreezeUserAlert extends AdminAlert implements Serializable {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    // Question by Tingyu: should the number parameter be in type int?
    private String username; // username of the user
    private int lent; // amount user has lent
    private int borrowed; // amount user has borrowed
    private int thresholdRequired; // difference needed between lent and borrowed

    public FreezeUserAlert(String username, int lent, int borrowed, int thresholdRequired){
        this.username = username;
        this.lent = lent;
        this.borrowed = borrowed;
        this.thresholdRequired = thresholdRequired;
    }

    public String getUsername(){ return username; }

    public int getLent(){ return lent; }

    public int getBorrowed(){ return borrowed; }

    public void setUsername(String username){ this.username = username; }

    public void setLent(int lent){ this.lent = lent; }

    public void setBorrowed(int borrowed){ this.borrowed = borrowed; }

    public int getThresholdRequired(){
        return this.thresholdRequired;
    }


}
