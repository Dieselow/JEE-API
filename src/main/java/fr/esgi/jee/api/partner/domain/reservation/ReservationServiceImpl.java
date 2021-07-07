package fr.esgi.jee.api.partner.domain.reservation;

import fr.esgi.jee.api.users.domain.User;
import fr.esgi.jee.api.users.domain.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserServiceImpl userService;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public ReservationServiceImpl(ReservationRepository reservationRepository, UserServiceImpl userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
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
    public Reservation update(Reservation reservation, String token) {
        User user = this.userService.getUserFromToken(token);
        Optional<Reservation> _reservation = findById(reservation.getId());

        if(!_reservation.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found !");
        }
        if(!user.getId().equals(_reservation.get().getOwner())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
        }

        _reservation.get().setInvitedUsers(reservation.getInvitedUsers());
        return reservationRepository.save(_reservation.get());
    }

    @Override
    public void delete(String id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(String id){
        return reservationRepository.findById(id);
    }
}
