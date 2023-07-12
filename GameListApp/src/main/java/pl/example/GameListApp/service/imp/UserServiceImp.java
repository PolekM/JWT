package pl.example.GameListApp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.entity.User;
import pl.example.GameListApp.repository.UserRepository;
import pl.example.GameListApp.service.UserService;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserDetails() {
        String CurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User bymail = userRepository.findBymail(CurrentUser);
        System.out.println(bymail);
        return bymail;
    }
}
