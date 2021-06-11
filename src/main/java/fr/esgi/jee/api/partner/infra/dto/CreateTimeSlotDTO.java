package fr.esgi.jee.api.partner.infra.dto;

import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTimeSlotDTO {
    TimeSlot timeSlot;
    Partner partner;
}
