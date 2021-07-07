package fr.esgi.jee.api.partner.domain.timeslot;

import fr.esgi.jee.api.partner.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "timeslot")
public class TimeSlot {
    @Id
    private String id;

    @Field(value = "start_date")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private long startDate;

    @Field(value = "end_date")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private long endDate;

    private int seats;

    @DBRef
    private Reservation reservation;
}
