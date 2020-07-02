package AlertPack;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExpirationAlert extends UserAlert implements Serializable {
    protected LocalDateTime dueDate;
    protected String tradeString;
    protected String username;
    protected int tradeId;

    public ExpirationAlert(LocalDateTime dueDate, String tradeString, String username, int tradeId){
        super();
        this.dueDate = dueDate;
        this.tradeString = tradeString;
        this.username = username;
        this.tradeId = tradeId;
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

    public int getTradeId() {
        return tradeId;
    }

    /**
     *
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return "The following TemporaryTrade has expired at" + dueDate + "\n" + tradeString;
    }
}
