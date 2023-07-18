package pl.example.GameListApp.Expection;

public class ChangePasswordException extends Exception {

    public ChangePasswordException(String message) {
        super(message);
    }

    public ChangePasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
