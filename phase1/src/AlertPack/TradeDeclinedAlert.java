package AlertPack;

import java.io.Serializable;

public class TradeDeclinedAlert extends UserAlert implements Serializable {
    protected String decliningUserName;
    protected int tradeID;

    public TradeDeclinedAlert(String decliningUserName, int tradeID){
        super();
        this.decliningUserName = decliningUserName;
        this.tradeID = tradeID;
    }

    /**
     *
     * @return the username of the user who declines the trade
     */
    public String getDecliningUserName(){
        return this.decliningUserName;
    }

    /**
     *
     * @return the ID of the trade declined by a user
     */
    public int getTradeID(){
        return this.tradeID;
    }
}
