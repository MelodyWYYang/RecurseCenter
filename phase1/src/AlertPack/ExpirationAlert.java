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

    public LocalDateTime getDueDate() {
        return dueDate;
    }


    public String getUsername() {
        return username;
    }

    public int getTradeId() {
        return tradeId;
    }


}
