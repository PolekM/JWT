package pl.example.GameListApp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.Expection.ChangePasswordException;
import pl.example.GameListApp.Expection.UserException;
import pl.example.GameListApp.dto.BoardDto;
import pl.example.GameListApp.dto.ChangePasswordDTO;
import pl.example.GameListApp.dto.UserDto;
import pl.example.GameListApp.entity.Board;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.repository.BoardRepository;
import pl.example.GameListApp.repository.UserRepository;
import pl.example.GameListApp.service.UserService;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImp(UserRepository userRepository, BoardRepository boardRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getUserDetails() {
        User user = findCurrentUser();
        UserDto userDto = new UserDto();
        userDto.setName(user.getMail());
        userDto.setRole(user.getUserRole().toString());
        return userDto;
    }


    @Override
    public List<BoardDto> getUserGame(String username) throws UserException {
        User user = userRepository.findBymail(username);
        if (user == null) {
            throw new UserException("user isn`t exist", new InstanceNotFoundException());
        }
        return user.getBoards().stream().map(this::boardDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardDto> addUserGame(long id) throws BoardException {
        User user = findCurrentUser();
        Optional<Board> game = boardRepository.findById(id);
        if (game.isEmpty()) {
            throw new BoardException("Board isn`t exist", new InstanceNotFoundException());
        }
        Set<Board> boards = user.getBoards();
        boards.add(game.get());
        userRepository.save(user);
        return boards.stream().map(this::boardDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardDto> removeUserGame(long id) throws BoardException {
        User user = findCurrentUser();
        Optional<Board> game = boardRepository.findById(id);
        if (game.isEmpty()) {
            throw new BoardException("Board isn`t exist", new InstanceNotFoundException());
        }
        Set<Board> boards = user.getBoards();
        boards.remove(game.get());
        userRepository.save(user);
        return boards.stream().map(this::boardDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> changePassword(ChangePasswordDTO changePasswordDTO) throws ChangePasswordException {
        User user = findCurrentUser();
        if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
        } else {
            throw new ChangePasswordException("Old Password is Wrong", new IllegalAccessException());
        }
        if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRepeatNewPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ChangePasswordException("New Password aren`t the same", new IllegalArgumentException());
        }

        return ResponseEntity.ok("Password has been changed");
    }

    public BoardDto boardDto(Board boards) {
        BoardDto boardDto = new BoardDto();

        boardDto.setName(boards.getName());
        boardDto.setGameType(boards.getGameType());

        return boardDto;
    }

    public User findCurrentUser() {
        return userRepository.findBymail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
