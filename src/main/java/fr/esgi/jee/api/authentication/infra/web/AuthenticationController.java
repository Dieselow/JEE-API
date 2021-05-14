package fr.esgi.jee.api.authentication.infra.web;

import fr.esgi.jee.api.authentication.login.LoginDTO;
import fr.esgi.jee.api.authentication.login.LoginResponseDTO;
import fr.esgi.jee.api.authentication.security.TokenProvider;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManager;
    private final UserServiceImpl userService;

    public AuthenticationController(TokenProvider tokenProvider,
                                    AuthenticationManagerBuilder authenticationManager,
                                    UserServiceImpl userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword());

        authenticationManager.getObject().authenticate(authenticationToken);

        String token = tokenProvider.createToken(
                loginDTO.getEmail(),
                this.userService.findUserByEmail(loginDTO.getEmail()).getRoles());


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