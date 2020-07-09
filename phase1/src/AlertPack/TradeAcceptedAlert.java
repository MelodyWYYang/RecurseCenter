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

    /**
     *
     * @return the username of the user who accepts the trade
     */
    public String getAcceptingUsername(){
        return this.acceptingUsername;
    }

    /**
     *
     * @return the ID of the trade accepted
     */
    public int getTradeID(){
        return tradeID;
    }

}