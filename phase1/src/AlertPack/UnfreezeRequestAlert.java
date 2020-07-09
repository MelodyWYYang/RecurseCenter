package AlertPack;

import java.io.Serializable;

public class UnfreezeRequestAlert extends AdminAlert implements Serializable {
    //author: Callan Murphy in group 0110 for CSC207H1 summer 2020 project
    private String username; // username of the user
    private int lent; // amount user has lent
    private int borrowed; // amount user has borrowed
    private int thresholdRequired; // difference needed between lent and borrowed

    public UnfreezeRequestAlert(String username, int lent, int borrowed, int thresholdRequired){
        this.username = username;
        this.lent = lent;
        this.borrowed = borrowed;
        this.thresholdRequired = thresholdRequired;
    }

    /**
     *
     * @return the username of the user who sends the unfreeze request
     */
    public String getUsername(){ return username; }

    /**
     *
     * @return the number of the items lent by the user
     */
    public int getLent(){ return lent; }

    /**
     *
     * @return the number of items borrowed by the user
     */
    public int getBorrowed(){ return borrowed; }

    /**
     *
     * @return the number of items that a user is required to lend before borrowing
     */
    public int getThresholdRequired(){
        return this.thresholdRequired;
    }

}
