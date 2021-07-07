package fr.esgi.jee.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public Pbkdf2PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder();
    }
}
