package fr.esgi.jee.api.partner.infra.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.esgi.jee.api.partner.domain.Partner;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateTimeSlotDTO {
    public TimeSlot timeSlot;
    public String partnerId;
}
