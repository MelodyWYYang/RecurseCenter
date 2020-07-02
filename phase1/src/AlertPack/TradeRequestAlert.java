package AlertPack;

import java.io.Serializable;

public class TradeRequestAlert extends UserAlert implements Serializable {
    protected String senderUserName;
    protected String tradeString;
    protected int tradeID;

    public TradeRequestAlert(String senderUserName, int tradeID, String tradeString){
        super();
        this.senderUserName = senderUserName;
        this.tradeID = tradeID;
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
     * @return username of the person who has proposed the trade.
     */
    public String getSenderUserName() {
        return senderUserName;
    }

    public int getTradeID(){ return tradeID; }

    /**
     *
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return senderUserName + " has proposed the following trade: \n" + tradeString + "\n";
    }
}
