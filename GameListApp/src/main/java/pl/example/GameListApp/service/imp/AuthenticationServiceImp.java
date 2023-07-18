package pl.example.GameListApp.service.imp;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.Expection.CustomAuthenticationException;
import pl.example.GameListApp.component.CustomUserDetails;
import pl.example.GameListApp.dto.JwtDto;
import pl.example.GameListApp.dto.RegisterDto;
import pl.example.GameListApp.entity.Role;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.repository.UserRepository;
import pl.example.GameListApp.service.AuthenticationService;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private  final AuthenticationManager authenticationManager;

    public AuthenticationServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
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

    public ResponseEntity<String> register(RegisterDto registerDto) throws CustomAuthenticationException {
        User userInDB = userRepository.findBymail(registerDto.getMail());
        if(userInDB!= null)
            throw new CustomAuthenticationException("email is busy");
        if(!registerDto.getPassword().equals(registerDto.getRepeatPassword()))
            throw new CustomAuthenticationException("New Password aren`t the same");
        userRepository.save(user(registerDto));
        return ResponseEntity.ok("Yor account has been created");
    }

    public User user(RegisterDto registerDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setMail(registerDto.getMail());
        user.setUserRole(Role.ROLE_USER);
        return user;
    }
}
