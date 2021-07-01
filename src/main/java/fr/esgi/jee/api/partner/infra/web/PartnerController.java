package fr.esgi.jee.api.partner.infra.web;

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
@RequestMapping("partner")
public class PartnerController {

    private final UserServiceImpl userService;
    private final PartnerServiceImpl partnerService;
    private final TimeSlotServiceImpl timeSlotService;

    public PartnerController(PartnerServiceImpl partnerService, UserServiceImpl userServiceImpl, TimeSlotServiceImpl timeSlotService) {
        this.partnerService = partnerService;
        this.userService = userServiceImpl;
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody CreatePartnerDTO createPartner) {
        var requieredRole = "PARTNER";
        User user = userService.findUserById(createPartner.getUserId());
        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals(requieredRole))){
            Partner createdPartner = partnerService.addPartner(createPartner.getPartner(), user);
            return new ResponseEntity<>(createdPartner, HttpStatus.CREATED);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You dont have the partner role.");
    }

    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners() {
        List partners = partnerService.findAll();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Partner>> getPartnersFromUserId(@PathVariable String id) {
        List partners = partnerService.findByOwnerId(id);
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @PostMapping("{partnerId}/timeslots")
    public ResponseEntity<TimeSlot> addTimeSlot(@RequestHeader(value="Authorization") String token, @PathVariable String partnerId, @RequestBody TimeSlot timeslot) {
        User user = userService.getUserFromToken(token);
        Optional<Partner> partner = partnerService.findById(partnerId);
        if(!partner.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "partner not found");
        }

        if(!user.getPartners().stream().anyMatch(p -> p.getId().equals(partner.get().getId()))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you arn't the owner of this partner");
        }

        if(timeslot.getStartDate() < new Date().getTime() / 1000){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "can't add a timeslot in the past");
        }

        TimeSlot created = timeSlotService.createTimeSlot(timeslot, partnerId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("{id}/timeslots")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots(@PathVariable String id) {

        Optional<Partner> partner = partnerService.findById(id);
        if (partner.isPresent()){
            List<TimeSlot> slots = partner
                    .get()
                    .getTimeSlots()
                    .stream()
                    .filter(t -> t.getReservation() == null)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(slots, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "partner not found");
    }

    @PostMapping("timeslotRange")
    public ResponseEntity<List<TimeSlot>> addTimeSlotByRange(@RequestBody CreateTimeSlotRangeDTO createTimeSlotRangeDTO) {


        if(createTimeSlotRangeDTO.getStartDate() < System.currentTimeMillis()){

            throw new ResponseStatusException(HttpStatus.CONFLICT, "can't add a timeslot in the past");
        }

        List<TimeSlot> createdSlots = timeSlotService.createTimeSlotByRange(createTimeSlotRangeDTO);
        return new ResponseEntity<>(createdSlots, HttpStatus.CREATED);
    }

    @PostMapping("test")
    public ResponseEntity<List<TimeSlot>> test(@RequestBody CreateTimeSlotDTO createTimeSlotDTO) {
        var res = timeSlotService.test(createTimeSlotDTO.getTimeSlot().getStartDate(), createTimeSlotDTO.getTimeSlot().getEndDate());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("{id}/prettifySlotDate")
    public ResponseEntity<List<Map<String,String>>> test(@PathVariable String id) {
        Optional<Partner> partner = partnerService.findById(id);
        if (partner.isPresent()){
            List slots = partner
                    .get()
                    .getTimeSlots()
                    .stream()
                    .filter(t -> t.getReservation().equals(null) && t.getStartDate() > System.currentTimeMillis())
                    .map(p -> {
                        Map<String, String> res = new HashMap<>();

                        var start = new Date(p.getStartDate());
                        var end = new Date(p.getEndDate());

                        res.put("start", start.toString());
                        res.put("end", end.toString());

                        return res;
                    })
                    .collect(Collectors.toList());
            return new ResponseEntity<>(slots, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "partner not found");

    }
}

