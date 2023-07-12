package pl.example.GameListApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.example.GameListApp.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
}
