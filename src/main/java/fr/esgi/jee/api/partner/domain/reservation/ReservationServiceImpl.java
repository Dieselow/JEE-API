package fr.esgi.jee.api.partner.domain.reservation;

import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import fr.esgi.jee.api.users.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation create(User creator){

        return reservationRepository.save(
                Reservation.builder()
                        .invitedUsers(new ArrayList<>())
                        .owner(creator.getId())
                        .build()
        );
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }
    
    public Optional<Reservation> findById(String id){
        return reservationRepository.findById(id);
    }


}
