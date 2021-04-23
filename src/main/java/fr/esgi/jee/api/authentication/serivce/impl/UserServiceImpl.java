package fr.esgi.jee.api.authentication.serivce.impl;

import fr.esgi.jee.api.authentication.model.User;
import fr.esgi.jee.api.authentication.repository.UserRepository;
import fr.esgi.jee.api.authentication.serivce.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(
                User.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .birthDate(user.getBirthDate())
                        .createDate(null)
                        .closeDate(null)
                        .lastLoginDate(null)
                        .token(null)
                        .roles(null)
                        .build()
        );
    }
}
