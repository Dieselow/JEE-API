package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.reservation.Reservation;
import fr.esgi.jee.api.partner.domain.reservation.ReservationServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import fr.esgi.jee.api.partner.infra.dto.CreatePartnerDTO;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotDTO;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotService;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import fr.esgi.jee.api.partner.infra.dto.CreatePartnerDTO;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotDTO;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final UserServiceImpl userService;
    private final PartnerServiceImpl partnerService;
    private final TimeSlotServiceImpl timeSlotService;
    private final ReservationServiceImpl reservationService;

    public TimeSlotController(PartnerServiceImpl partnerService, UserServiceImpl userServiceImpl, TimeSlotServiceImpl timeSlotService, ReservationServiceImpl reservationServiceImpl) {
        this.partnerService = partnerService;
        this.userService = userServiceImpl;
        this.timeSlotService = timeSlotService;
        this.reservationService = reservationServiceImpl;
    }

    @PostMapping("{id}/reservation")
    public ResponseEntity<Reservation> addReservation(@RequestHeader(value="Authorization") String token, @PathVariable String id) {
        User user = userService.getUserFromToken(token);
        Optional<TimeSlot> timeslot = timeSlotService.findById(id);
        if(!timeslot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "timeslot not found");
        }

        if(timeslot.get().getReservation() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation already exists in this slot");
        }

        Reservation created = reservationService.create(user);
        timeslot.get().setReservation(created);
        timeSlotService.update(timeslot.get());

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
