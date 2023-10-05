package clipperms.collection.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvise {

    @ExceptionHandler(value = {IncorrectInputException.class})
    public ResponseEntity<Object> handleException(IncorrectInputException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {DatabaseFailedOperationException.class})
    public ResponseEntity<Object> handleException(DatabaseFailedOperationException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<Object> handleException(IOException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
