package pl.example.GameListApp.Expection;

public class CustomAuthenticationException extends Exception{

    public CustomAuthenticationException(String message) {
        super(message);
    }

    public CustomAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
