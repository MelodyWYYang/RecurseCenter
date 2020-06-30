public class TradeRequestCancelledAlert extends Alert{
    protected String tradeString;

    public TradeRequestCancelledAlert(String tradeString){
        super();
        this.tradeString = tradeString;
    }

    @Override
    public String toString() {
        return " The following trade request has been cancelled as one of the users is no longer in possession of " +
                "a item in the proposed trade. /n" + tradeString ;
    }
}
