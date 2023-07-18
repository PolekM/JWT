package pl.example.GameListApp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.Expection.BoardException;
import pl.example.GameListApp.entity.Board;
import pl.example.GameListApp.repository.BoardRepository;
import pl.example.GameListApp.service.BoardService;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImp implements BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardServiceImp(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> getAllGames() {
        return boardRepository.findAll();
    }

    @Override
    public Board addNewGame(Board board) {
        return boardRepository.save(board);

    }

    @Override
    public Optional<Board> removeGame(Long id) throws BoardException {
        Optional<Board> deletedBoard = boardRepository.findById(id);
        if(deletedBoard.isEmpty()){
            throw new BoardException("Board isn`t exist", new InstanceNotFoundException());
        }
        deletedBoard.ifPresent(boardRepository::delete);
        return deletedBoard;
    }

    @Override
    public Optional<Board> updateGame(Board board) throws BoardException {
        Optional<Board> updatedBoard = boardRepository.findById(board.getId());
        if(updatedBoard.isEmpty()){
            throw new BoardException("Board isn`t exist",new InstanceNotFoundException());
        }
        updatedBoard.get().setName(board.getName());
        updatedBoard.get().setGameType(board.getGameType());
        boardRepository.save(updatedBoard.get());
        return updatedBoard;
    }
}
