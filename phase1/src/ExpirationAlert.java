import java.time.LocalDateTime;

public class ExpirationAlert extends Alert{
    protected LocalDateTime dueDate;
    protected int tradeID;

    public ExpirationAlert(LocalDateTime dueDate, int tradeId){
        this.dueDate = dueDate;
        this.tradeID = tradeId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public int getTradeID() {
        return tradeID;
    }

    @Override
    public String toString() {
        return "This TemporaryTrade (" + tradeID + ") has expired at" + dueDate;
    }
}
