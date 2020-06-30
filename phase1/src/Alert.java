import java.io.Serializable;
public abstract class Alert implements Serializable{
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    private static int idGenerator = 0;
    protected int alertID;


    public Alert() {
        alertID = idGenerator;
        idGenerator++;
    }

    /**
     *
     * @return ID number of the alert.
     */
    public int getAlertID() {
        return alertID;
    }
}
