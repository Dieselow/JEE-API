package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.Partner;

import java.util.Date;
import java.util.List;

public interface TimeSlotService {
    TimeSlot createTimeSlot(TimeSlot timeSlot, Partner partner);
    List<TimeSlot> createTimeSlotByRange(Date startDate, Date endDate, int slotDurationMin, Partner partner);
}
