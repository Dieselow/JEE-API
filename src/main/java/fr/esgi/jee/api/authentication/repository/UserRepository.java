package fr.esgi.jee.api.authentication.repository;

import fr.esgi.jee.api.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByFirstName(String firstName);
}
