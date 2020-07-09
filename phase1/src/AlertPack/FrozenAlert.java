package AlertPack;

import java.awt.*;
import java.io.Serializable;

public class FrozenAlert extends UserAlert implements Serializable {

    protected int numBorrowedofUser;
    protected int numLentofUser;
    protected int threshholdNumofUser;

    public FrozenAlert(int numBorrowed, int numLent, int threshholdNum){
        super();
        numBorrowedofUser = numBorrowed;
        numLentofUser = numLent;
        threshholdNumofUser = threshholdNum;
    }

    /**
     *
     * @return The number of items the user has borrowed.
     */
    public int getNumBorrowedofUser() {
        return numBorrowedofUser;
    }

    /**
     *
     * @return the number of items the user has lent.
     */
    public int getNumLentofUser() {
        return numLentofUser;
    }

    /**
     *
     * @return the threshold of lent - borrowed that each user should be greater than or equal to.
     */
    public int getThreshholdNumofUser() {
        return threshholdNumofUser;
    }


}
