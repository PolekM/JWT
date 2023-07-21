package pl.example.GameListApp.service.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.Expection.UserException;
import pl.example.GameListApp.dto.BoardDto;
import pl.example.GameListApp.dto.UserDto;
import pl.example.GameListApp.entity.Board;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.repository.BoardRepository;
import pl.example.GameListApp.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.example.GameListApp.entity.Role.ROLE_USER;

class UserServiceImpTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BoardRepository boardRepository;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @InjectMocks
    UserServiceImp userServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void userDetailsShouldReturnDetailsAboutUser() {
        //given
        User user = preparedUser();

        //when

        when(userRepository.findBymail(any())).thenReturn(user);
        UserDto userDto = userServiceImp.getUserDetails();

        //then
        assertThat(userDto.getName(), is("user@user.pl"));
        assertThat(userDto.getRole(), is("ROLE_USER"));

    }

    @Test
    void getUsrBoardShouldReturnListOfTwoGame() throws UserException {
        //given
        User user = preparedUser();
        //when
        when(userRepository.findBymail(any())).thenReturn(user);
        List<BoardDto> boards = userServiceImp.getUserGame("user@user.pl");
        //then
        assertThat(boards, hasSize(2));
        assertThat(boards.get(0).getName(), is("root"));
        assertThat(boards.get(1).getName(), is("ticket to ride"));
    }

    @Test
    void getUserBoardWithWrongUserNameShouldThrowUserException() throws UserException {
        //given
        User user = preparedUser();
        //when

        //then
        UserException userException = assertThrows(UserException.class, () -> userServiceImp.getUserGame(user.getMail()));
        assertThat(userException.getMessage(), is("user isn`t exist"));
    }

    @Test
    void addGameToUserShouldReturnThreeBoardObjectInList() throws BoardException {
        //given
        User user = preparedUser();
        Board board = new Board("tested board", "testedGameType");
        //when
        when(boardRepository.findById(any())).thenReturn(Optional.of(board));
        when(userRepository.findBymail(any())).thenReturn(user);
        userServiceImp.addUserGame(1);
        //then

        assertThat(user.getBoards(), hasSize(3));
        assertThat((user.getBoards().stream().toList()).get(2), is(board));
        verify(userRepository).save(user);

    }

    @Test
    void addGameToUserShouldThrowExceptionWhenBoardNotExist() throws BoardException {
        //given
        User user = preparedUser();
        //when
        when(userRepository.findBymail(any())).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.empty());
        //then
        BoardException boardException = assertThrows(BoardException.class, () -> userServiceImp.addUserGame(1));
        assertThat(boardException.getMessage(), is("Board isn`t exist"));
    }

    @Test
    void removeOneGameFromUserShouldReturnListWithOneObject() throws BoardException {
        //given
        User user = preparedUser();
        Board board = new Board("tested board", "testedGameType");
        //when
        when(userRepository.findBymail(any())).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(user.getBoards().stream().findFirst());
        userServiceImp.removeUserGame(1);
        //then
        assertThat(user.getBoards(), hasSize(1));
        assertThat(user.getBoards().stream().findFirst().get().getName(), is("ticket to ride"));
        verify(userRepository).save(user);

    }

    @Test
    void removeGameToUserShouldThrowExceptionWhenBoardNotExist() throws BoardException {
        User user = preparedUser();
        //when
        when(userRepository.findBymail(any())).thenReturn(user);
        when(boardRepository.findById(any())).thenReturn(Optional.empty());
        //then
        BoardException boardException = assertThrows(BoardException.class, () -> userServiceImp.removeUserGame(1));
        assertThat(boardException.getMessage(), is("Board isn`t exist"));
        assertThat(boardException, instanceOf(BoardException.class));
    }

    public User preparedUser() {
        Set<Board> boardList = new LinkedHashSet<>();
        boardList.add(new Board("root", "Strategy"));
        boardList.add(new Board("ticket to ride", "economy"));
        User user = new User();
        user.setUserRole(ROLE_USER);
        user.setMail("user@user.pl");
        user.setBoards(boardList);

        return user;
    }


}