package pl.example.GameListApp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.entity.Board;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.repository.BoardRepository;
import pl.example.GameListApp.repository.UserRepository;
import pl.example.GameListApp.service.UserService;

import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public User getUserDetails() {
        String CurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User bymail = userRepository.findBymail(CurrentUser);
        System.out.println(bymail);
        return bymail;
    }

    @Override
    public void addGameToUser(Long userId, Long boardId) {
        Set<Board> boardSet;
        User user = userRepository.findById(userId).get();
        Board board = boardRepository.findById(boardId).get();
        boardSet = user.getBoards();
        boardSet.add(board);
        user.setBoards(boardSet);
        userRepository.save(user);
    }
}
