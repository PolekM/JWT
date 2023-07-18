package pl.example.GameListApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.dto.BoardByNameDto;
import pl.example.GameListApp.entity.Board;
import pl.example.GameListApp.service.BoardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    //todo - Games - ALL - wyświetlenie wsyzstkich planszówek

    @GetMapping("games")
    public List<Board> getAllGame() {
        return boardService.getAllGames();
    }

    //todo - addNewGame - ADMIN - dodanie nowej gry do bazy danych
    @PostMapping("/new")
    public Board addNewGame(@RequestBody Board board) {
        return boardService.addNewGame(board);
    }

    //todo - Search - ALL - Wyszukiwanie gier po nazwie
    @PostMapping("/search")
    public List<Board> findBoardByName(@RequestBody BoardByNameDto boardByNameDto){
        return boardService.findBoardByName(boardByNameDto);
    }

    //todo - removeGame - ADMIN - usunięcie planszówki z danych
    @DeleteMapping("/remove/{gameId}")
    public Optional<Board> removeGame(@PathVariable(name = "gameId") Long id) throws BoardException {
        return boardService.removeGame(id);

    }

    //todo - updateGame - ADMIN - zmiana wartości w grze
    @PutMapping("/update")
    public Optional<Board> updateGame(@RequestBody Board board) throws BoardException {
        return boardService.updateGame(board);
    }
}
