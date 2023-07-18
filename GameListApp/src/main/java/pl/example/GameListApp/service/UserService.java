package pl.example.GameListApp.service;

import org.springframework.http.ResponseEntity;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.Expection.ChangePasswordException;
import pl.example.GameListApp.Expection.UserException;
import pl.example.GameListApp.dto.BoardDto;
import pl.example.GameListApp.dto.ChangePasswordDTO;
import pl.example.GameListApp.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserDetails();


    List<BoardDto> getUserGame(String username) throws UserException;

    List<BoardDto> addUserGame(long id) throws BoardException;

    List<BoardDto> removeUserGame(long id) throws BoardException;

    ResponseEntity<String> changePassword(ChangePasswordDTO changePasswordDTO) throws ChangePasswordException;
}
