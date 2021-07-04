package fr.esgi.jee.api.partner.domain.reservation;

import fr.esgi.jee.api.users.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(
                Reservation.builder()
                        .invitedUsers(reservation.getInvitedUsers())
                        .owner(reservation.getOwner())
                        .build()
        );
    }

    @Override
    public Reservation update(Reservation reservation) {
        Optional<Reservation> _reservation = findById(reservation.getId());
        if(!_reservation.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        _reservation.get().setInvitedUsers(reservation.getInvitedUsers());
        return reservationRepository.save(_reservation.get());
    }

    public void delete(String id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }
    
    public Optional<Reservation> findById(String id){
        return reservationRepository.findById(id);
    }


}
