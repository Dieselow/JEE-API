package fr.esgi.jee.api.authentication.domain.login;

import lombok.Data;

@Data
public class LoginDTO {

    private String email;
    private String password;
}

