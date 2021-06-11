package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.PartnerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public TimeSlot createTimeSlot(TimeSlot timeSlot, Partner partner) {
        TimeSlot createdTimeSlot = timeSlotRepository.save(
                TimeSlot.builder()
                        .startDate(timeSlot.getStartDate())
                        .endDate(timeSlot.getEndDate())
                        .seats(timeSlot.getSeats())
                        .isReserved(timeSlot.isReserved())
                        .build()
        );
        List<TimeSlot> slots = new ArrayList<>();
        slots.add(createdTimeSlot);
        //partnerService.addTimeSlots(slots, partner);
        return createdTimeSlot;
    }

    @Override
    public List<TimeSlot> createTimeSlotByRange(Date startDate, Date endDate, int slotDurationMin, Partner partner) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        final long slotDuration = (long) slotDurationMin * 60 * 1000;

        for (long t = startDate.getTime(); t + slotDuration < endDate.getTime() ; t+=slotDurationMin) {
            System.out.println(new Date(t));
        }
        //partnerService.addTimeSlots(timeSlots, partner);


        return timeSlots;
    }
}
