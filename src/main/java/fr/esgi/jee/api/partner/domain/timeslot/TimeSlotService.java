package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;

import java.util.List;

public interface TimeSlotService {
    TimeSlot createTimeSlot(TimeSlot timeSlot);
    List<TimeSlot> buildTimeSlotByRange(CreateTimeSlotRangeDTO createTimeSlotRangeDTO);
}
