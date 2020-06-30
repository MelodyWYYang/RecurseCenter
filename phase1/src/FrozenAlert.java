import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;

public class FrozenAlert extends UserAlert implements Serializable {
    protected LocalDateTime timeOfFreeze;
    protected int numBorrowedofUser;
    protected int numLentofUser;
    protected int threshholdNumofUser;

    public FrozenAlert(LocalDateTime timeOfFreeze, int numBorrowed, int numLent, int threshholdNum){
        super();
        this.timeOfFreeze = timeOfFreeze;
        numBorrowedofUser = numBorrowed;
        numLentofUser = numLent;
        threshholdNumofUser = threshholdNum;
    }

    public LocalDateTime getTimeOfFreeze() {
        return timeOfFreeze;
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

    /**
     *
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return "Your account has been frozen by administrator at " + timeOfFreeze.toString() +
                "/n" + "You have borrowed: " + numBorrowedofUser + "items" +
                "/n" + "You have lent" + numLentofUser + "items" +
                "/n" + "You need to lend" + threshholdNumofUser + "items before you can borrow";
    }


}
