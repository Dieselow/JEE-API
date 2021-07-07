package fr.esgi.jee.api.partner.domain;

import fr.esgi.jee.api.geolocaliztion.models.Address;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Document(collection = "partner")
public class Partner {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String phoneNumber;
    @Indexed(unique = true)
    private Address address;
    @Field(value = "create_date")
    private Date createDate;
    @Field(value = "close_date")
    private Date closeDate;
    @Field(value = "photo_url")
    private String photoUrl;
    @Field(value = "time_slots")
    @DBRef
    private List<TimeSlot> timeSlots;
}
