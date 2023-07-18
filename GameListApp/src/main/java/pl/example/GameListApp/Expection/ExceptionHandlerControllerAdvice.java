package pl.example.GameListApp.Expection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {


    @ExceptionHandler(BoardException.class)
    public ResponseEntity<?> boardHandler(BoardException boardException){
        Error error = new Error(boardException.getMessage(),boardException.getCause());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userHandler(UserException userException){
        Error error = new Error(userException.getMessage(),userException.getCause());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<?> changePasswordExceptionHandler(ChangePasswordException changePasswordException){
        Error error = new Error(changePasswordException.getMessage(), changePasswordException.getCause());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> AuthenticationException(CustomAuthenticationException customAuthenticationException){
        Error error = new Error(customAuthenticationException.getMessage(),customAuthenticationException.getCause());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }




}
