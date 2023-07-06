package pl.example.GameListApp.service.imp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.component.CustomUserDetails;
import pl.example.GameListApp.dto.JwtDto;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private  final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(JwtDto jwtDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtDto.getMail(),jwtDto.getPassword()));
        User user = userRepository.findBymail(jwtDto.getMail());
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String token  = jwtService.generateToken(customUserDetails);
        System.out.println(token);
        return  token;

    }
}
