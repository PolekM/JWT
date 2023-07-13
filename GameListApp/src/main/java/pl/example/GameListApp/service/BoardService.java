package pl.example.GameListApp.service;


import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    List<Board> getAllGames();

    Board addNewGame(Board board);

    Optional<Board> removeGame(Long id) throws BoardException;

    Optional<Board> updateGame(Board board) throws BoardException;
}
