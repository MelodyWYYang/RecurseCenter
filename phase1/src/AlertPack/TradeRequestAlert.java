package AlertPack;

import java.io.Serializable;

public class TradeRequestAlert extends UserAlert implements Serializable {
    protected String senderUserName;
    protected int tradeID;
    protected boolean isTempTrade;

    public TradeRequestAlert(String senderUserName, int tradeID, boolean isTempTrade){
        super();
        this.senderUserName = senderUserName;
        this.tradeID = tradeID;
        this.isTempTrade = isTempTrade;
    }

    /**
     *
     * @return username of the person who has proposed the trade.
     */
    public String getSenderUserName() {
        return senderUserName;
    }

    /**
     *
     * @return the ID of the trade request proposed
     */
    public int getTradeID(){ return tradeID; }

    public boolean getIsTempTrade(){
        return this.isTempTrade;
    }
}
