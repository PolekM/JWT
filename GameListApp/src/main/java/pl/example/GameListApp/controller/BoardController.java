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


    //Permission - ALL. Show all games
    @GetMapping("games")
    public List<Board> getAllGame() {
        return boardService.getAllGames();
    }

    //Permission - ADMIN. Add new game
    @PostMapping("/new")
    public Board addNewGame(@RequestBody Board board) {
        return boardService.addNewGame(board);
    }

    //Permission - ALL. Search Game by Name
    @PostMapping("/search")
    public List<Board> findBoardByName(@RequestBody BoardByNameDto boardByNameDto) {
        return boardService.findBoardByName(boardByNameDto);
    }

    //Permission - ADMIN. Remove game
    @DeleteMapping("/remove/{gameId}")
    public Optional<Board> removeGame(@PathVariable(name = "gameId") Long id) throws BoardException {
        return boardService.removeGame(id);

    }

    //Permission - ADMIN. Update game
    @PutMapping("/update")
    public Optional<Board> updateGame(@RequestBody Board board) throws BoardException {
        return boardService.updateGame(board);
    }
}
