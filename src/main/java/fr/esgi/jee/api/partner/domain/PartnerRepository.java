package fr.esgi.jee.api.partner.domain;

import fr.esgi.jee.api.users.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends MongoRepository<Partner, String> {
    Partner findByName(String name);
}
