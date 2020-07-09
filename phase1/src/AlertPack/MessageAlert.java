package AlertPack;

import java.io.Serializable;

public class MessageAlert extends UserAlert implements Serializable {
    //author: Louis Scheffer V in group 0110 for CSC207H1 summer 2020 project
    protected String message;
    protected String senderUsername;

    public MessageAlert(String message, String senderUsername){
        super();
        this.message = message;
        this.senderUsername = senderUsername;
    }

    /**
     *
     * @return the username of the sender.
     */
    public String getSenderUsername(){
        return this.senderUsername;
    }

    /**
     *
     * @return the message being sent.
     */
    public String getMessage(){
        return this.message;
    }
}
