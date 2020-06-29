public class TradeRequestAlert {
    protected String userName;

    public TradeRequestAlert(String userName){
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName + " has made a trade request with you/edited an existing trade";
    }
}
