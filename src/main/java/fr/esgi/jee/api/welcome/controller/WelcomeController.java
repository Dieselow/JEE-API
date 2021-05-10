package fr.esgi.jee.api.welcome.controller;

import fr.esgi.jee.api.welcome.model.Welcome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping("/welcome")
@RestController
public class WelcomeController {

    @GetMapping
    public ResponseEntity<Welcome> welcome() {
        Welcome welcome = Welcome.builder()
                .team("Les Bros Ⓒ")
                .project("JEE-API")
                .version("1.0.0")
                .members(Arrays.asList("Julien DA CORTE","Louis DUMONT","Maxime D'HARBOULLE"))
                .build();

        return new ResponseEntity<>(welcome, HttpStatus.OK);
    }
}
