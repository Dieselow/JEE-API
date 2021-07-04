package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.reservation.Reservation;
import fr.esgi.jee.api.partner.domain.reservation.ReservationServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotServiceImpl timeSlotService;
    private final ReservationServiceImpl reservationService;

    public TimeSlotController(TimeSlotServiceImpl timeSlotService, ReservationServiceImpl reservationServiceImpl) {
        this.timeSlotService = timeSlotService;
        this.reservationService = reservationServiceImpl;
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<TimeSlot> cancelReservation(@PathVariable String id) {
        return new ResponseEntity<>(timeSlotService.cancelReservation(id), HttpStatus.OK);
    }

    @PostMapping("{id}/reservation")
    public ResponseEntity<Reservation> addReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        Optional<TimeSlot> timeslot = timeSlotService.findById(id);

        if(!timeslot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(timeslot.get().getReservation() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation already exists in this slot");
        }

        Reservation created = reservationService.create(reservation);
        timeslot.get().setReservation(created);
        timeSlotService.update(timeslot.get());

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


}

