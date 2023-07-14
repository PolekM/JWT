package pl.example.GameListApp.service;

import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.Expection.UserException;
import pl.example.GameListApp.dto.BoardDto;
import pl.example.GameListApp.entity.User;

import java.util.List;

public interface UserService {
    User getUserDetails();



    List<BoardDto> getUserGame(String username) throws UserException;

    List<BoardDto> addUserGame(long id) throws BoardException;

    List<BoardDto> removeUserGame(long id) throws BoardException;
}
