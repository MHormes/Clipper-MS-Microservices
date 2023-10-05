package clipperms.collection.exceptions;

public class DatabaseFailedOperationException extends Exception{
    public DatabaseFailedOperationException(String errorMessage){
        super(errorMessage);
    }
}
