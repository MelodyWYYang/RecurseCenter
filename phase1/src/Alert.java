import java.io.Serializable;
public abstract class Alert implements Serializable{
    private static int idGenerator = 0;
    protected int alertID;

    public Alert() {
        alertID = idGenerator;
        idGenerator++;
    }

    public int getAlertID() {
        return alertID;
    }
}
