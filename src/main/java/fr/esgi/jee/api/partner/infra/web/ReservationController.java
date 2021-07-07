package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.reservation.Reservation;
import fr.esgi.jee.api.partner.domain.reservation.ReservationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("reservation")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    public ReservationController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @PutMapping
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation) {
        return new ResponseEntity<>(this.reservationService.update(reservation), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable String id) {
        Optional<Reservation> reservation = this.reservationService.findById(id);
        if(!reservation.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "reservation not found");
        }
        return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteReservation(@PathVariable String id) {
        try {
            this.reservationService.delete(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
