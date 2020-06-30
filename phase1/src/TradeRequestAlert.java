public class TradeRequestAlert extends Alert{
    protected String userName;
    protected String tradeString;

    public TradeRequestAlert(String userName, String tradeString){
        super();
        this.userName = userName;
        this.tradeString = tradeString;
    }

    @Override
    public String toString() {
        return userName + " has proposed the following trade: /n" + tradeString;
    }
}
