package AlertPack;

import java.io.Serializable;

public class TradeRequestCancelledAlert extends UserAlert implements Serializable {
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected int tradeID;

    public TradeRequestCancelledAlert(int tradeID){
        super();
        this.tradeID = tradeID;
    }

    /**
     *
     * @return String which details information about the trade.
     */
    public int getTradeID() {
        return tradeID;
    }

}
