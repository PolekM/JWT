package pl.example.GameListApp.Expection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {


    @ExceptionHandler
    public ResponseEntity<?> boardHandler(BoardException boardException){
        Error error = new Error(boardException.getMessage(),boardException.getCause());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
