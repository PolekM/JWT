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

    //Permission - Authentication User. Show user details
    @GetMapping("/details")
    public UserDto getUserDetails() {
        return userService.getUserDetails();
    }

    //Permission - Authentication User. Changing Password
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws ChangePasswordException {
        return userService.changePassword(changePasswordDTO);
    }


    //Permission - ALL. Get list selected user
    @GetMapping("/{username}")
    public List<BoardDto> getUserGame(@PathVariable(name = "username") String username) throws UserException {
        return userService.getUserGame(username);
    }

    //Permission - Authentication User. Add game to user list
    @PutMapping("/add/{gameId}")
    public List<BoardDto> addUserGame(@PathVariable(name = "gameId") long id) throws BoardException {
        return userService.addUserGame(id);
    }

    //Permission - Authentication User. remove game form user list
    @DeleteMapping("/remove/{gameId}")
    public List<BoardDto> removeUserGame(@PathVariable(name = "gameId") long id) throws BoardException {
        return userService.removeUserGame(id);
    }


}
