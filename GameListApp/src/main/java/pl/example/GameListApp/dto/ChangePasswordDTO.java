package pl.example.GameListApp.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    
    String oldPassword;
    String newPassword;
    String repeatNewPassword;
}
