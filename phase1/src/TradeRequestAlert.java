public class TradeRequestAlert extends Alert{
    protected String userName;
    protected String tradeString;

    public TradeRequestAlert(String userName, String tradeString){
        super();
        this.userName = userName;
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
     * @return username of the person who has proposed the trade.
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return the final text of the alert.
     */
    @Override
    public String toString() {
        return userName + " has proposed the following trade: /n" + tradeString;
    }
}
