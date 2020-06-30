public class TradeCancelledAlert {
    protected String tradeString;

    public TradeCancelledAlert(String userName, String tradeString){
        super();
        this.tradeString = tradeString;
    }

    @Override
    public String toString() {
        return " The following pending trade has been cancelled as one of the users is no longer in possession of " +
                "a item in the proposed trade. /n" + tradeString ;
    }
}
