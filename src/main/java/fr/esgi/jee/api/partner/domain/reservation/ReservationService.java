package fr.esgi.jee.api.partner.domain.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation create(Reservation reservation);
    Reservation update(Reservation reservation, String token);
    void delete(String id);
    List<Reservation> findAll();
    Optional<Reservation> findById(String id);
}
