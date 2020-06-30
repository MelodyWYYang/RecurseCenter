import java.io.Serializable;

public class TradeRequestCancelledAlert extends Alert implements Serializable {
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected String tradeString;

    public TradeRequestCancelledAlert(String tradeString){
        super();
        this.tradeString = tradeString;
    }

    /**
     *
     * @return String which details information about the trade.
     */
    public String getTradeString() {
        return tradeString;
    }

    /**
     *
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return " The following trade request has been cancelled as one of the users is no longer in possession of " +
                "a item in the proposed trade. /n" + tradeString ;
    }
}
