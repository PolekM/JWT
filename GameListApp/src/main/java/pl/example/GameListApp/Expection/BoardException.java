package pl.example.GameListApp.Expection;


public class BoardException extends Exception {

    public BoardException(String message) {
        super(message);
    }

    public BoardException(String message, Throwable cause) {
        super(message, cause);
    }

}

