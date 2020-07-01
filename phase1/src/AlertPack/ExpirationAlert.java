package AlertPack;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExpirationAlert extends UserAlert implements Serializable {
    protected LocalDateTime dueDate;
    protected String tradeString;
    protected String username;

    public ExpirationAlert(LocalDateTime dueDate, String tradeString, String username){
        super();
        this.dueDate = dueDate;
        this.tradeString = tradeString;
        this.username = username;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getTradeString() {
        return tradeString;
    }

    public String getUsername() {
        return username;
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
