package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.domain.timeslot.reservation.Reservation;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final PartnerServiceImpl partnerService;

    /**
     * Constructor Injection
     * better than @Autowired
     */
    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository, PartnerServiceImpl partnerService) {
        this.timeSlotRepository = timeSlotRepository;
        this.partnerService = partnerService;
    }

    @Override
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {
        TimeSlot createdTimeSlot = timeSlotRepository.save(
                TimeSlot.builder()
                        .startDate(timeSlot.getStartDate())
                        .endDate(timeSlot.getEndDate())
                        .seats(timeSlot.getSeats())
                        .reservation(null)
                        .build()
        );
        return createdTimeSlot;
    }

    @Override
    public List<TimeSlot> buildTimeSlotByRange(CreateTimeSlotRangeDTO createTimeSlotRangeDTO) {

        List<TimeSlot> timeSlots = new ArrayList<>();

        for (long t = createTimeSlotRangeDTO.getStartDate(); t + createTimeSlotRangeDTO.getDuration() < createTimeSlotRangeDTO.getEndDate() ; t += createTimeSlotRangeDTO.getDuration() + createTimeSlotRangeDTO.getPause()) {
            timeSlots.add(
                    TimeSlot.builder()
                        .startDate(t)
                        .endDate(t + createTimeSlotRangeDTO.getDuration() - 1)
                        .seats(createTimeSlotRangeDTO.getSeats())
                        .reservation(null)
                        .build()
            );
        }
        return timeSlots;
    }

    public Optional<TimeSlot> findById(String id){
        return timeSlotRepository.findById(id);
    }

    public TimeSlot update(TimeSlot timeSlot){
        return timeSlotRepository.save(timeSlot);
    }

    public List<TimeSlot> getByReservationOwner(String ownerId){
        return this.timeSlotRepository.findByReservation_Owner(ownerId);
    }

    public boolean isReservationConflict(TimeSlot timeSlot, List<TimeSlot> list) {
    for(TimeSlot _timeSlot : list) {
            if ((timeSlot.getStartDate() <= _timeSlot.getStartDate() && timeSlot.getEndDate() <= _timeSlot.getEndDate())
                || (timeSlot.getStartDate() <= _timeSlot.getEndDate() && timeSlot.getEndDate() >= _timeSlot.getStartDate())
                || (timeSlot.getStartDate() <= _timeSlot.getStartDate() && timeSlot.getEndDate() >= _timeSlot.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public TimeSlot createReservation(String timeSlotId, Reservation reservation) {
        Optional<TimeSlot> slot = findById(timeSlotId);

        if(!slot.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "timeSlot not found");
        }

        List<TimeSlot> userTimeslots = getByReservationOwner(reservation.getOwner());
        if(isReservationConflict(slot.get(), userTimeslots)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "reservation date conflict");
        }

        slot.get()
            .setReservation(
                Reservation.builder()
                        .invitedUsers(reservation.getInvitedUsers())
                        .owner(reservation.getOwner())
                        .build()
        );
        return update(slot.get());
    }

    public TimeSlot updateReservation(String timeSlotId, Reservation reservation) {
        Optional<TimeSlot> slot = findById(timeSlotId);

        if(!slot.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "timeSlot not found");
        }

        TimeSlot dbSlot = slot.get();

        dbSlot.setReservation(reservation);

        return update(dbSlot);
    }
}
