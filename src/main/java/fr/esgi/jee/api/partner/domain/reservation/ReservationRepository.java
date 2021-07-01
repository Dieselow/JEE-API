package fr.esgi.jee.api.partner.domain.reservation;

import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
}
