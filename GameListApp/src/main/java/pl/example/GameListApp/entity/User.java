package pl.example.GameListApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String mail;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    @ManyToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    @JoinTable(
            name = "board_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "board_id",referencedColumnName = "id"))
    private Set<Board> boards = new HashSet<>();


}
