package pl.example.GameListApp.service;

import pl.example.GameListApp.entity.User;

public interface UserService {
    User getUserDetails();

    void addGameToUser(Long userId, Long boardId);
}
