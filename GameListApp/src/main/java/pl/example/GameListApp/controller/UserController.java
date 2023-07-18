package pl.example.GameListApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.Expection.ChangePasswordException;
import pl.example.GameListApp.Expection.UserException;
import pl.example.GameListApp.dto.BoardDto;
import pl.example.GameListApp.dto.ChangePasswordDTO;
import pl.example.GameListApp.dto.UserDto;
import pl.example.GameListApp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public UserDto getUserDetails(){
        return userService.getUserDetails();
    }

    //todo - changePassword - auth - zmiana hasła użytkownika
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws ChangePasswordException {
        return userService.changePassword(changePasswordDTO);
    }


    //todo - getGame  - permitAll - wyświetlenie wsyzstkich gier danego użytkownika
    @GetMapping("/{username}")
    public List<BoardDto> getUserGame(@PathVariable(name = "username") String username) throws UserException {
        return userService.getUserGame(username);
    }
    //todo - addGame - auth - dodanie gry do listy użytkownika
    @PutMapping("/add/{gameId}")
    public List<BoardDto> addUserGame(@PathVariable(name = "gameId") long id) throws BoardException {
        return userService.addUserGame(id);
    }

    //todo - removeGame - auth - usuniecie gry z listy użytkownika
    @DeleteMapping("/remove/{gameId}")
    public List<BoardDto> removeUserGame(@PathVariable(name = "gameId") long id) throws BoardException {
        return userService.removeUserGame(id);
    }


}
