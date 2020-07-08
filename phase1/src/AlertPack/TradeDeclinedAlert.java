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

    public String getDecliningUserName(){
        return this.decliningUserName;
    }

    public int getTradeID(){
        return this.tradeID;
    }
}
