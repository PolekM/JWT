package pl.example.GameListApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.example.GameListApp.dto.JwtDto;
import pl.example.GameListApp.service.imp.AuthenticationService;

@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public String authenticate(@RequestBody JwtDto jwtDto){
        return authenticationService.authenticate(jwtDto);
    }

    @PostMapping("/register")
    public String register(){
        return null;
        //todo rejestracja - ALL - Rejestracja do aplikacji
    }

}
