package pl.example.GameListApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //todo usunąć testowe
    @GetMapping("/user")
    public String testUser(){
        return "user";
    }
    //todo usunąć testowe
    @GetMapping("/admin")
    public String testAdmin(){
        return "admin";
    }

    @GetMapping("/current")
    public User getUserDetails(){
        return userService.getUserDetails();
    }

    //todo - changePassword - auth - zmiana hasła użytkownika

    //todo - getGame  - permitAll - wyświetlenie wsyzstkich gier danego użytkownika

    //todo - addGame - auth - dodanie gry do listy użytkownika

    //todo - removeGame - auth - usuniecie gry z listy użytkownika


    @PutMapping("/{userId}/board/{boardId}")
    public String addGameToUser(@PathVariable Long userId,@PathVariable Long boardId){
        userService.addGameToUser(userId,boardId);
        return "dodano";
    }


}
