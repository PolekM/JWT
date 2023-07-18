package pl.example.GameListApp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gameType;

    public Board(String name, String gameType) {
        this.name = name;
        this.gameType = gameType;
    }

    //    @ManyToMany(mappedBy = "boards")
//    private Set<User> user = new HashSet<>();


}
