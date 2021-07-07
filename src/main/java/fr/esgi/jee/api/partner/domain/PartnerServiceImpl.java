package fr.esgi.jee.api.partner.domain;

import fr.esgi.jee.api.geolocaliztion.GeolocaliztionService;
import fr.esgi.jee.api.geolocaliztion.models.Address;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.infra.dto.CreatingPartnerDTO;
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
    private final GeolocaliztionService geolocaliztionService;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public PartnerServiceImpl(PartnerRepository partnerRepository, UserServiceImpl userService, GeolocaliztionService geolocaliztionService) {
        this.partnerRepository = partnerRepository;
        this.userService = userService;
        this.geolocaliztionService = geolocaliztionService;
    }

    @Override
    public Partner addPartner(CreatingPartnerDTO partner, String strAddress, User user) {

        Address address = null;
        try{
            address = geolocaliztionService.addressToGeoloc(strAddress).getData().get(0);
        }catch (Exception e){
        }

        Partner createdPartner = partnerRepository.save(
                Partner.builder()
                        .name(partner.getName())
                        .phoneNumber(partner.getPhoneNumber())
                        .address(address)
                        .createDate(new Date())
                        .closeDate(null)
                        .timeSlots(new ArrayList<>())
                        .photoUrl(partner.photoUrl)
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
