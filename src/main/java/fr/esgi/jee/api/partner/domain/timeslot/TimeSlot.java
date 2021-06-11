package fr.esgi.jee.api.partner.domain.timeslot;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@Document(collection = "timeslot")
public class TimeSlot {
    @Id
    private String id;

    @Field(value = "start_date")
    private Date startDate;

    @Field(value = "end_date")
    private Date endDate;

    private int seats;

    @Field(value = "is_reserved")
    private boolean isReserved;
}
