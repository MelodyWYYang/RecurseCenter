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

    public int getNumBorrowedofUser() {
        return numBorrowedofUser;
    }

    public int getNumLentofUser() {
        return numLentofUser;
    }

    public int getThreshholdNumofUser() {
        return threshholdNumofUser;
    }


}
