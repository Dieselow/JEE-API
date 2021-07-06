package fr.esgi.jee.api.authentication.infra.web;

import fr.esgi.jee.api.authentication.login.LoginDTO;
import fr.esgi.jee.api.authentication.login.LoginResponseDTO;
import fr.esgi.jee.api.authentication.security.TokenProvider;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final UserServiceImpl userService;

    public AuthenticationController(TokenProvider tokenProvider,
                                    UserServiceImpl userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        if(!this.userService.checkUserLogin(loginDTO)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "wrong login or password");
        }

        String token = tokenProvider.createToken(this.userService.findUserByEmail(loginDTO.getEmail()));

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        User _userExist = userService.findUserByEmail(user.getEmail());
        if(_userExist == null) {
            try {
                User _user = userService.addUser(user);
                return new ResponseEntity<>(_user, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}