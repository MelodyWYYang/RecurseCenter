public class MessageAlert extends Alert{
    protected String message;
    protected String senderUsername;

    public MessageAlert(String message, String senderUsername){
        this.message = message;
        this.senderUsername = senderUsername;
    }
    public String getSenderUsername() {
        return senderUsername;
    }

    public String getMessage() {
        return message;
    }
    public String toString(){
        return "From: " + senderUsername + "/n" + message;
    }
}
