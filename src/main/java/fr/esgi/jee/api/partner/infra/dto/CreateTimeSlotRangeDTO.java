package fr.esgi.jee.api.partner.infra.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CreateTimeSlotRangeDTO {

    public long startDate;

    public long endDate;

    public int slotDurationMin;
    public int seats;
    public String partnerId;
}
