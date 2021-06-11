package fr.esgi.jee.api.partner.domain.timeslot;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends MongoRepository<TimeSlot, String> {
}
