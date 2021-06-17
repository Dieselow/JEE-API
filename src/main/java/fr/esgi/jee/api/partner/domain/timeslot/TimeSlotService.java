package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface TimeSlotService {
    TimeSlot createTimeSlot(TimeSlot timeSlot, String partnerId);
    List<TimeSlot> createTimeSlotByRange(CreateTimeSlotRangeDTO createTimeSlotRangeDTO);
}
