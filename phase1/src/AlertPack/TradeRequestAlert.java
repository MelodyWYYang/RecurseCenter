package AlertPack;

import java.io.Serializable;

public class TradeRequestAlert extends UserAlert implements Serializable {
    protected String senderUserName;
    protected int tradeID;

    public TradeRequestAlert(String senderUserName, int tradeID){
        super();
        this.senderUserName = senderUserName;
        this.tradeID = tradeID;
    }

    /**
     *
     * @return username of the person who has proposed the trade.
     */
    public String getSenderUserName() {
        return senderUserName;
    }

    public int getTradeID(){ return tradeID; }

}
