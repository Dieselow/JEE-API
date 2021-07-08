package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.timeslot.reservation.Reservation;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "timeslot")
public class TimeSlot {
    @Id
    private String id;

    @Field(value = "start_date")
    private long startDate;

    @Field(value = "end_date")
    private long endDate;

    private int seats;

    private Reservation reservation;
}
