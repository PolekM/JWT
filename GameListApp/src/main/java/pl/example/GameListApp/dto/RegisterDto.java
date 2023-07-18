package pl.example.GameListApp.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
@Data
public class RegisterDto {

    @Email(message = "Incorrect email address")
    private String mail;
    private String password;
    private String repeatPassword;
}
