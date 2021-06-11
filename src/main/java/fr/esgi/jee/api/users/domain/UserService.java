package fr.esgi.jee.api.users.domain;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user);
    User findUserByEmail(String email);
    User findUserById(String id);
    List<User> getUsers();
}
