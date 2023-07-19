package pl.example.GameListApp.service.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.entity.Board;
import pl.example.GameListApp.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BoardServiceImpTest {

    @Mock
    BoardRepository boardRepository;

    @InjectMocks
    BoardServiceImp boardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllGamesShouldReturnListOfThreeObjects() {
        //given
        List<Board> preparedGames = preparedListOfGames();
        //when
        when(boardRepository.findAll()).thenReturn(preparedGames);
        List<Board> allGames = boardService.getAllGames();
        //then
        assertThat(allGames, hasSize(3));
        assertThat(allGames, is(preparedGames));
    }

    @Test
    void addGameShouldSaveTheGameToRepository() {
        //given
        Board board = new Board("root", "Strategy");
        //when
        boardService.addNewGame(board);
        //then
        verify(boardRepository).save(board);
    }

    @Test
    void removeGameShouldRemoveGameFromRepository() throws BoardException {
        //given
        Board board = new Board("root", "Strategy");
        //when
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        boardService.removeGame(1L);
        //then
        verify(boardRepository).delete(board);

    }

    @Test
    void removeGameWithNotExistBoardGameShouldThrowException() {
        //given
        Board board = new Board("root", "Strategy");
        //when
        when(boardRepository.findById(10L)).thenReturn(Optional.empty());

        //then
        assertThrows(BoardException.class, () -> boardService.updateGame(board));
    }

    @Test
    void updateGameWithNoExistBoardGameShouldThrowException() {
        //given

        //when
        when(boardRepository.findById(10L)).thenReturn(Optional.empty());

        //then
        assertThrows(BoardException.class, () -> boardService.removeGame(10L));
    }

    @Test
    void updateGameShouldReturnGameWithNewName() throws BoardException {
        Board existingBoard = new Board(1L, "Existing Board", "Strategy");
        Board updatedBoard = new Board(1L, "Updated Board", "New Game Type");

        // when
        when(boardRepository.findById(1L)).thenReturn(Optional.of(existingBoard));
        when(boardRepository.save(existingBoard)).thenReturn(existingBoard);
        Optional<Board> result = boardService.updateGame(updatedBoard);

        // then
        assertEquals("Updated Board", result.get().getName());
        assertEquals("New Game Type", result.get().getGameType());

    }


    private List<Board> preparedListOfGames() {

        Board firstBoard = new Board("Root", "Strategy");
        Board secondBoard = new Board("7 miracle wonder duel", "For 2 Player");
        Board thirdBoard = new Board("Robinson Crusoe", "Cooperative");
        return new ArrayList<>(List.of(firstBoard, secondBoard, thirdBoard));
    }

}