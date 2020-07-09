package AlertPack;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExpirationAlert extends UserAlert implements Serializable {
    protected LocalDateTime dueDate;
    protected String username;
    protected int tradeId;

    public ExpirationAlert(LocalDateTime dueDate, String username, int tradeId){
        super();
        this.dueDate = dueDate;
        this.username = username;
        this.tradeId = tradeId;
    }

    /**
     *
     * @return The date & time at which the temporary trade is over and the items should be returned.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     *
     * @return returns the username of the user this alert is being sent to.
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the ID of the temporary trade.
     */
    public int getTradeId() {
        return tradeId;
    }


}
