package AlertPack;

import java.io.Serializable;

public class TradeAcceptedAlert extends UserAlert implements Serializable {
    protected String acceptingUsername;
    protected int tradeID;

    public TradeAcceptedAlert(String acceptingUsername, int tradeID) {
        super();
        this.acceptingUsername = acceptingUsername;
        this.tradeID = tradeID;
    }

    public String getAcceptingUsername(){
        return this.acceptingUsername;
    }

    public int getTradeID(){
        return tradeID;
    }

}