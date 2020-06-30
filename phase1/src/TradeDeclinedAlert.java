public class TradeDeclinedAlert {
    protected String userName;
    protected String tradeString;

    public TradeDeclinedAlert(String userName, String tradeString){
        super();
        this.userName = userName;
        this.tradeString = tradeString;
    }

    @Override
    public String toString() {
        return userName + " has declined the following trade request. /n" + tradeString ;
    }
}
