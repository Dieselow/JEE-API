package fr.esgi.jee.api.authentication.serivce.impl;

import fr.esgi.jee.api.authentication.model.User;
import fr.esgi.jee.api.authentication.repository.UserRepository;
import fr.esgi.jee.api.authentication.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        User _user = userRepository.save(
                User.builder()
                        .firstName("Les Bros Ⓒ")
                        .lastName("JEE-API")
                        .email("1.0.0")
                        .phoneNumber(01222324)
                        .build()
        );
        return _user;
    }
}
