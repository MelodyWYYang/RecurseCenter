package AlertPack;

import java.io.Serializable;

public class TradeAcceptedAlert extends UserAlert implements Serializable {
    protected String acceptingUsername;
    protected String tradeString;

    public TradeAcceptedAlert(String acceptingUsername, String tradeString) {
        super();
        this.acceptingUsername = acceptingUsername;
        this.tradeString = tradeString;
    }

    public String toString() {
        return acceptingUsername + " has accepted the following trade request: \n" + tradeString;
    }
}