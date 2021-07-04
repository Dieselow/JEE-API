package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
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
    public TimeSlot createTimeSlot(TimeSlot timeSlot, String partnerId) {
//NOT NEEDED KEEP HERE FOR NOW
//        var conflictingSlots = timeSlotRepository.findConflicting(timeSlot.getStartDate(), timeSlot.getEndDate());
//        if (conflictingSlots != null && conflictingSlots.size() > 0){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "can't add a timeslot a the same time as another one");
//        }
        TimeSlot createdTimeSlot = timeSlotRepository.save(
                TimeSlot.builder()
                        .startDate(timeSlot.getStartDate())
                        .endDate(timeSlot.getEndDate())
                        .seats(timeSlot.getSeats())
                        .reservation(null)
                        .build()
        );

        List<TimeSlot> slots = new ArrayList<>();
        slots.add(createdTimeSlot);
        partnerService.addTimeSlots(slots, partnerId);
        return createdTimeSlot;
    }

    @Override
    public List<TimeSlot> createTimeSlotByRange(CreateTimeSlotRangeDTO createTimeSlotRangeDTO) {

        //TODO: MAXIME
        List<TimeSlot> timeSlots = new ArrayList<>();
        final long slotDuration = (long) createTimeSlotRangeDTO.getSlotDurationMin() * 60 * 1000;

        System.out.println(createTimeSlotRangeDTO);
        System.out.println(createTimeSlotRangeDTO.getEndDate());
        System.out.println(slotDuration);

        for (long t = createTimeSlotRangeDTO.getStartDate(); t + slotDuration < createTimeSlotRangeDTO.getEndDate() ; t+=slotDuration) {
            System.out.println(new Date(t));
        }
        //partnerService.addTimeSlots(timeSlots, partner);


        return timeSlots;
    }

    public List<TimeSlot> findAll(){
        return timeSlotRepository.findAll();
    }

    public Optional<TimeSlot> findById(String id){
        return timeSlotRepository.findById(id);
    }

    public TimeSlot update(TimeSlot timeSlot){
        Optional<TimeSlot> dbTimeSlot = this.findById(timeSlot.getId());
        if(!dbTimeSlot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        TimeSlot updated = this.fillTimeSlot(dbTimeSlot.get(), timeSlot);
        return timeSlotRepository.save(updated);
    }


    public TimeSlot fillTimeSlot(TimeSlot finalTimeSlot, TimeSlot newTimeSlot){
        if(newTimeSlot.getReservation() != null)
            finalTimeSlot.setReservation(newTimeSlot.getReservation());

        return finalTimeSlot;
    }

    @Override
    public TimeSlot cancelReservation(String id) {
        Optional<TimeSlot> timeSlot = findById(id);
        if(!timeSlot.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        timeSlot.get().setReservation(null);
        return this.timeSlotRepository.save(timeSlot.get());
    }
}
