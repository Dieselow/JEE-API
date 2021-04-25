package fr.esgi.jee.api.users.infra.web;

import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return null;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping("/user")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        try {
            User _user = userService.addUser(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
