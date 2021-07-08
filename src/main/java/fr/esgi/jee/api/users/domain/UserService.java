package fr.esgi.jee.api.users.domain;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(User user) throws NoSuchAlgorithmException;
    User findUserByEmail(String email);
    User findUserById(String id);
    List<User> getUsers();
}
