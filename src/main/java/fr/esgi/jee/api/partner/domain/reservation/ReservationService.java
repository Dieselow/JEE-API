package fr.esgi.jee.api.partner.domain.reservation;

public interface ReservationService {
    Reservation create(Reservation reservation);
    Reservation update(Reservation reservation);
    void delete(String id);
}
