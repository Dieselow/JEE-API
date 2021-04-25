package fr.esgi.jee.api.users.domain;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor Injection
     * better than @Autowired
     */
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
                        .birthDate(null)
                        .createDate(null)
                        .closeDate(null)
                        .lastLoginDate(null)
                        .token(null)
                        .roles(null)
                        .build()
        );
    }
}
