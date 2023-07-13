package pl.example.GameListApp.Expection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private final String message;
    private final Throwable throwable;
}
