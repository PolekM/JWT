package pl.example.GameListApp.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.example.GameListApp.Expection.CustomAuthenticationException;
import pl.example.GameListApp.dto.JwtDto;
import pl.example.GameListApp.dto.RegisterDto;
import pl.example.GameListApp.service.AuthenticationService;

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
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) throws CustomAuthenticationException {
        return authenticationService.register(registerDto);
    }

}
