//An exception to be called by the constructor of Trade. Signals to the UserManager that this trade
//request is invalid.
public class CannotBorrowException extends Exception{

    public CannotBorrowException(String message){
        super(message);
    }
}
