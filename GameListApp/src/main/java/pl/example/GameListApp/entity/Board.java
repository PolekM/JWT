package pl.example.GameListApp.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gameType;

    @ManyToMany(mappedBy = "boards",cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Set<User> user = new HashSet<>();


}
