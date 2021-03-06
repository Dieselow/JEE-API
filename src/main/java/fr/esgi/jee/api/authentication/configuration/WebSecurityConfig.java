package fr.esgi.jee.api.authentication.configuration;

import fr.esgi.jee.api.authentication.security.JWTFilter;
import fr.esgi.jee.api.authentication.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/welcome").permitAll()
                    .antMatchers("/reservation*").permitAll()
                    .antMatchers(HttpMethod.GET, "/partner*").permitAll()
                    .antMatchers(HttpMethod.GET, "/partner/*/timeslots").permitAll()
                    .antMatchers(HttpMethod.POST, "/partner/*/timeslots").hasAuthority("PARTNER")
                    .antMatchers(HttpMethod.POST, "/partner").hasAuthority("PARTNER")
                .anyRequest()
                    .authenticated()
                    .and()
                .addFilterBefore(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
