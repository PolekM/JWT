package pl.example.GameListApp.specification;

import org.springframework.data.jpa.domain.Specification;
import pl.example.GameListApp.dto.BoardByNameDto;
import pl.example.GameListApp.entity.Board;

public class BoardSpecification {

    public static Specification<Board> boardSpecificationFindByName(BoardByNameDto boardByNameDto){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+boardByNameDto.getBoardName().toLowerCase() + "%");
    }
}
