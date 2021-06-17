package fr.esgi.jee.api.partner.domain;
import fr.esgi.jee.api.authentication.login.RoleRepository;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final UserServiceImpl userService;
    private final PartnerRepository partnerRepository;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public PartnerServiceImpl(PartnerRepository partnerRepository, UserServiceImpl userService) {
        this.partnerRepository = partnerRepository;
        this.userService = userService;
    }

    @Override
    public Partner addPartner(Partner partner, User user) {

        Partner createdPartner = partnerRepository.save(
                Partner.builder()
                        .name(partner.getName())
                        .phoneNumber(partner.getPhoneNumber())
                        .address(partner.getAddress())
                        .createDate(new Date())
                        .closeDate(null)
                        .timeSlots(new ArrayList<>())
                        .build()
        );

        var partners = user.getPartners() == null ? new HashSet<Partner>() : user.getPartners();
        partners.add(createdPartner);
        user.setPartners(partners);
        userService.updateUser(user);

        return createdPartner;
    }

    public List<Partner> findAll(){
        return partnerRepository.findAll();
    }

    public List<Partner> findByOwnerId(String id){
        return new ArrayList<>(userService.findUserById(id).getPartners());
    }

    public Optional<Partner> findById(String id){
        return partnerRepository.findById(id);
    }

    public Partner addTimeSlots(List<TimeSlot> timeSlots, String partnerId){
        Optional<Partner> optionalPartner = this.findById(partnerId);
        if(optionalPartner.isPresent()){
            Partner dbPartner = optionalPartner.get();
            List<TimeSlot> slots = dbPartner.getTimeSlots();
            slots.addAll(timeSlots);
            dbPartner.setTimeSlots(slots);
            partnerRepository.save(dbPartner);
            return dbPartner;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find partner when adding timeslots to partner");
    }

}
