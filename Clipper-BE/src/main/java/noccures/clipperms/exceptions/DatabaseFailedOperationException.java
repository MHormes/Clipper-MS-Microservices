package noccures.clipperms.exceptions;

public class DatabaseFailedOperationException extends Exception{
    public DatabaseFailedOperationException(String errorMessage){
        super(errorMessage);
    }
}
