package AlertPack;

import java.io.Serializable;

public class TradeDeclinedAlert extends UserAlert implements Serializable {
    protected String decliningUserName;
    protected String tradeString;

    public TradeDeclinedAlert(String decliningUserName, String tradeString){
        super();
        this.decliningUserName = decliningUserName;
        this.tradeString = tradeString;
    }

    @Override
    public String toString() {
        return decliningUserName + " has declined the following trade request: /n" + tradeString ;
    }
}
