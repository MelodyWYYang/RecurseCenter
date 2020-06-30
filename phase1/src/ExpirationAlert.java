import java.io.Serializable;
import java.time.LocalDateTime;

public class ExpirationAlert extends UserAlert implements Serializable {
    protected LocalDateTime dueDate;
    protected String tradeString;

    public ExpirationAlert(LocalDateTime dueDate, String tradeString){
        super();
        this.dueDate = dueDate;
        this.tradeString = tradeString;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getTradeString() {
        return tradeString;
    }

    /**
     *
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return "The following TemporaryTrade has expired at" + dueDate + "/n" + tradeString;
    }
}
