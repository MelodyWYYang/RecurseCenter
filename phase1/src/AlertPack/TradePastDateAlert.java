package AlertPack;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TradePastDateAlert extends UserAlert implements Serializable {

    protected LocalDateTime dueDate;
    protected String tradeString;
    protected String username;
    protected int tradeId;

    public TradePastDateAlert(LocalDateTime dueDate, String tradeString, String username, int tradeId){
        this.dueDate = dueDate;
        this.tradeString = tradeString;
        this.username = username;
        this.tradeId = tradeId;
    }

    /**
     *
     * @return the due date of the trade.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     *
     * @return string of the trade.
     */
    public String getTradeString() {
        return tradeString;
    }

    /**
     *
     * @return the username of the user involved in the trade.
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return trade id.
     */
    public int getTradeId() {
        return tradeId;
    }

    /**
     *
     * @return the alert text.
     */
    @Override
    public String toString() {
        return "The following trade expired at" + dueDate + "\n" + tradeString;
    }
}
