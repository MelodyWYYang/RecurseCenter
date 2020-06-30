public class TradeCancelledAlert extends Alert {
    protected String tradeString;

    public TradeCancelledAlert(String tradeString){
        super();
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
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return " The following pending trade has been cancelled as one of the users is no longer in possession of " +
                "a item in the proposed trade. /n" + tradeString ;
    }
}
