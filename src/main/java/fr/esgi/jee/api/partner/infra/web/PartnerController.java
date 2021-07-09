package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import fr.esgi.jee.api.partner.infra.dto.CreatePartnerDTO;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        User user = userService.findUserById(createPartner.getUserId());
        Partner createdPartner = partnerService.addPartner(createPartner.getPartner(), createPartner.address, user);
        return new ResponseEntity<>(createdPartner, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners() {
        List<Partner> partners = partnerService.findAll();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Partner>> getPartnersFromUserId(@PathVariable String id) {
        List<Partner> partners = partnerService.findByOwnerId(id);
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Partner> findById(@PathVariable String id) {
        Optional<Partner> partner = partnerService.findById(id);
        if(!partner.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "partner not found");
        }
        return new ResponseEntity<>(partner.get(), HttpStatus.OK);
    }

    @PostMapping("{partnerId}/timeslots")
    public ResponseEntity<TimeSlot> addTimeSlot(@RequestHeader(value="Authorization") String token, @PathVariable String partnerId, @RequestBody TimeSlot timeslot) {
        User user = userService.getUserFromToken(token);

        if(!user.getPartners().stream().anyMatch(p -> p.getId().equals(partnerId))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you arn't the owner of this partner");
        }

        if(timeslot.getStartDate() < System.currentTimeMillis() / 1000){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "can't add a timeslot in the past");
        }

        TimeSlot created = timeSlotService.createTimeSlot(timeslot);

        List<TimeSlot> slots = new ArrayList<>();
        slots.add(created);
        partnerService.addTimeSlots(slots, partnerId);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("{partnerId}/timeslots/range")
    public ResponseEntity<Partner> addTimeSlotByRange(@RequestHeader(value="Authorization") String token, @PathVariable String partnerId, @RequestBody CreateTimeSlotRangeDTO createTimeSlotRangeDTO) {
        User user = userService.getUserFromToken(token);

        if(!user.getPartners().stream().anyMatch(p -> p.getId().equals(partnerId))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you arn't the owner of this partner");
        }

        if(createTimeSlotRangeDTO.getStartDate() < System.currentTimeMillis() / 1000){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "can't add a timeslot in the past");
        }

        List<TimeSlot> builtSlots = timeSlotService.buildTimeSlotByRange(createTimeSlotRangeDTO);
        for(TimeSlot slot : builtSlots){
            slot.setId(
                    timeSlotService.createTimeSlot(slot).getId()
            );
        }
        Partner resultPartner = partnerService.addTimeSlots(builtSlots, partnerId);

        return new ResponseEntity<>(resultPartner, HttpStatus.CREATED);
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


}

