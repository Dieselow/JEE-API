package fr.esgi.jee.api.controllers;

import fr.esgi.jee.api.models.Welcome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class WelcomeController {

    @GetMapping(path = "/")
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
