package pl.example.GameListApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.example.GameListApp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User findBymail(String mail);

}
