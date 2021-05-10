package fr.esgi.jee.api.authentication.login;

import lombok.Data;

@Data
public class LoginDTO {

    private String email;
    private String password;
}

