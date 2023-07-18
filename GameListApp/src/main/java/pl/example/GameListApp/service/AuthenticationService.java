package pl.example.GameListApp.service;

import org.springframework.http.ResponseEntity;
import pl.example.GameListApp.Expection.CustomAuthenticationException;
import pl.example.GameListApp.dto.JwtDto;
import pl.example.GameListApp.dto.RegisterDto;

public interface AuthenticationService {

    String authenticate(JwtDto jwtDto);
    ResponseEntity<String> register(RegisterDto registerDto) throws CustomAuthenticationException;
}
