package fr.esgi.jee.api.partner.infra.web;

import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotService;
import fr.esgi.jee.api.partner.infra.dto.CreatePartnerDTO;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotDTO;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partner")
public class PartnerController {

    private final UserServiceImpl userService;
    private final PartnerServiceImpl partnerService;

    public PartnerController(PartnerServiceImpl partnerService, UserServiceImpl userServiceImpl) {
        this.partnerService = partnerService;
        this.userService = userServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<Partner> createPartner(@RequestBody CreatePartnerDTO createPartner) {
        User user = userService.findUserById(createPartner.getUserId());
        if (user.getRoles().stream().anyMatch(role -> role.getRole().equals("PARTNER"))){
            Partner createdPartner = partnerService.addPartner(createPartner.getPartner(), user);
            return new ResponseEntity<>(createdPartner, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping()
    public ResponseEntity<List<Partner>> getAllPartners() {
        List partners = partnerService.findAll();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Partner>> getPartnersFromUserId(@PathVariable String id) {
        List partners = partnerService.findByOwnerId(id);
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

//    @PostMapping("timeslot")
//    public ResponseEntity<TimeSlot> addTimeSlot(@RequestBody CreateTimeSlotDTO createTimeSlotDTO) {
//        TimeSlot created = timeSlotService.createTimeSlot(createTimeSlotDTO.getTimeSlot(), createTimeSlotDTO.getPartner());
//        return new ResponseEntity<>(created, HttpStatus.CREATED);
//    }
}

