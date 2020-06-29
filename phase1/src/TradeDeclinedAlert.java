public class TradeDeclinedAlert {
    protected String userName;

    public TradeDeclinedAlert(String userName){
        super();
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName + " has declined your trade request.";
    }
}
