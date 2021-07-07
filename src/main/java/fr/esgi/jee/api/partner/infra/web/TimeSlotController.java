package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.reservation.Reservation;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("timeslots")
public class TimeSlotController {

    private final TimeSlotServiceImpl timeSlotService;
    private final UserServiceImpl userService;


    public TimeSlotController(TimeSlotServiceImpl timeSlotService, UserServiceImpl userService) {
        this.timeSlotService = timeSlotService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeSlot> getById(@PathVariable String id){
        Optional<TimeSlot> slot = timeSlotService.findById(id);
        if(!slot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(slot.get(), HttpStatus.OK);
    }

    @GetMapping("reservation/{ownerId}")
    public ResponseEntity<List<TimeSlot>> getSlotsByOwner(@PathVariable String ownerId){
        List<TimeSlot> slots = this.timeSlotService.getByReservationOwner(ownerId);
        return new ResponseEntity<>(slots, HttpStatus.OK);

    }

    @PostMapping("{id}/reservation")
    public ResponseEntity<TimeSlot> addReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        TimeSlot slot = timeSlotService.createReservation(id, reservation);
        return new ResponseEntity<>(slot, HttpStatus.CREATED);
    }


    @PutMapping("{id}/reservation")
    public ResponseEntity<TimeSlot> updateReservation(@RequestHeader(value="Authorization") String token, @PathVariable String id, @RequestBody Reservation reservation) {
        User user = userService.getUserFromToken(token);

        Optional<TimeSlot> timeslot = timeSlotService.findById(id);

        if(!timeslot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(!timeslot.get().getReservation().getOwner().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "can't modify reservation you arn't the owner of");
        }

        // TODO: call user timeslot list

        //        if(this.timeSlotService.isReservationConflict(timeslot.get())) {
        //            throw new ResponseStatusException(HttpStatus.CONFLICT, "you already have a reservation");
        //        }
        timeslot.get().setReservation(reservation);

        TimeSlot timeslotDb = timeSlotService.update(timeslot.get());

        return new ResponseEntity<>(timeslotDb, HttpStatus.OK);
    }

    @DeleteMapping("{id}/reservation")
    public ResponseEntity deleteReservation(@RequestHeader(value="Authorization") String token, @PathVariable String id) {
        User user = userService.getUserFromToken(token);

        Optional<TimeSlot> timeslot = timeSlotService.findById(id);

        if(!timeslot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(!timeslot.get().getReservation().getOwner().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "can't modify reservation you arn't the owner of");
        }

        TimeSlot timeslotDb = timeSlotService.updateReservation(id, null);

        return new ResponseEntity<>(timeslotDb, HttpStatus.NO_CONTENT);
    }
}

